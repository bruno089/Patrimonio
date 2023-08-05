package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.domain.services.LecturaArchivos.LectorArchivosContext;
import com.gabru.Patrimonio.domain.services.LecturaArchivos.LectorTipo;
import com.gabru.Patrimonio.api.dtos.TransactionDto;
import com.gabru.Patrimonio.api.dtos.MovimientosTotalesPorConceptoDto;
import com.gabru.Patrimonio.data.entities.Transaction;


import com.gabru.Patrimonio.data.entities.Usuario;
import com.gabru.Patrimonio.domain.exceptions.ConflictException;
import com.gabru.Patrimonio.domain.exceptions.NotFoundException;

import com.gabru.Patrimonio.data.repositories.TransactionRepository;
import com.gabru.Patrimonio.data.repositories.TransactionRepositoryCustom;
import com.gabru.Patrimonio.utils.business_services.FechaConverterService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.gabru.Patrimonio.utils.business_services.FechaConverterService.stringtoLocalDate;

@Controller
@AllArgsConstructor
public class TransactionService {
    TransactionRepository transactionRepository;
    TransactionRepositoryCustom transactionRepositoryCustom;
    CategoryService categoryService;
    UserDetailsServiceImpl userDetailsServiceImpl;

    /** CRUD **/
    public TransactionDto create ( TransactionDto transactionDto ) {

        LocalDate fecha = LocalDate.parse(transactionDto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Category newCategory =  categoryService.getConcepto(transactionDto.getConceptoDescripcion());

        Transaction transaction = Transaction.builder()
                .observacion(transactionDto.getObservacion())
                .importe(transactionDto.getImporte())
                .fecha(fecha)
                .alta(LocalDateTime.now())
                .category(newCategory)
                .usuario(userDetailsServiceImpl.getUsuarioAutenticado())
                .build();

        transactionRepository.save(transaction);

        return new TransactionDto(transaction);
    }
    public TransactionDto create ( List<TransactionDto> transactionDtoList ) {
        try {
            // Obtengo el usuario autenticado
            Usuario usuarioAutenticado = userDetailsServiceImpl.getUsuarioAutenticado();

            // Lista para almacenar los nuevos movimientos creados
            List<Transaction> nuevosTransactions = new ArrayList<>();

            // Recorro la lista de MovimientoDto recibida
            for (TransactionDto transactionDto : transactionDtoList) {
                // Parseo la fecha del movimientoDto
                LocalDate fecha = LocalDate.parse(transactionDto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                // Obtengo el Concepto correspondiente
                Category category = categoryService.getConcepto(transactionDto.getConceptoDescripcion());

                // Creo el nuevo Movimiento
                Transaction transaction = Transaction.builder()
                        .observacion(transactionDto.getObservacion())
                        .importe(transactionDto.getImporte())
                        .fecha(fecha)
                        .alta(LocalDateTime.now())
                        .category(category)
                        .usuario(usuarioAutenticado)
                        .build();

                // Agrego el nuevo Movimiento a la lista de nuevosMovimientos
                nuevosTransactions.add(transaction);
            }

            // Guardo todos los nuevos movimientos en la base de datos
            transactionRepository.saveAll(nuevosTransactions);

            // Retorno el primer MovimientoDto creado, podrías ajustar esto según tus necesidades
            return new TransactionDto(nuevosTransactions.get(0));

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error de parseo de fecha en un movimiento: " + e.getMessage());
        }catch (ConflictException e) {
            // Manejar las excepciones adecuadamente según la lógica de tu aplicación
            e.printStackTrace();
            throw new ConflictException("Error al agregar los movimientos: " + e.getMessage());
        } catch (Exception e) {
            // Otras excepciones generales que puedan ocurrir, como problemas en la BD, etc.
            e.printStackTrace();
            throw new RuntimeException("Error al agregar los movimientos: " + e.getMessage());
        }
    }
    public TransactionDto read ( Integer movimientoId ) {

        Transaction transaction = transactionRepository
                .findById(movimientoId)
                .orElseThrow(() -> new NotFoundException("No encontrado id: "+ movimientoId));

        return  new TransactionDto(transaction);
    }
    public TransactionDto update ( int id, TransactionDto transactionDto ) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new NotFoundException("No se encuentra el movimiento con ID: " + id));

        Category elCategory =  categoryService.getConcepto(transactionDto.getConceptoDescripcion()); //Todo Fix Break when is null

        transaction.setCategory(elCategory);
        transaction.setFecha(FechaConverterService.stringtoLocalDate(transactionDto.getFecha(), "dd/MM/yyyy"));
        transaction.setImporte(transactionDto.getImporte());
        transaction.setObservacion(transactionDto.getObservacion());
        transactionRepository.save(transaction);

        return new TransactionDto(transaction);
    }
    public void delete ( int movimientoId) {

        Transaction transaction = transactionRepository
                .findByIdAndUsuario(movimientoId, userDetailsServiceImpl.getUsuarioAutenticado())
                .orElseThrow(()-> new NotFoundException("No encontrado movimiento: " + movimientoId ));

        transactionRepository.delete(transaction);
    }
    public void CsvAMovimientoDtoList ( MultipartFile archivo, String tipoImportacion ){

        LectorArchivosContext lectorArchivosContext = new LectorArchivosContext(LectorTipo.CSV );

        List<TransactionDto> transactionDtos =  lectorArchivosContext.ejecutar(archivo);

        //this.agregar(movimientoDtos); //Todo check this

        transactionDtos.forEach(movimientoDto -> this.create( movimientoDto));
    }

    public List<TransactionDto> buscarMovimientosPorFecha( String fechaInicial, String fechaFinal) {
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<TransactionDto> movimientos = transactionRepository.findAllByFechaBetweenOrderByFecha(fechaIni,fechaFin);
       // List<MovimientoDto> movimientos = movimientoRepository.findAllByFechaBetweenAndUsuarioOrderByFecha(fechaIni,fechaFin,usuarioService.getUsuarioAutenticado());

        return movimientos;
    }
    public List<MovimientosTotalesPorConceptoDto> totalizador( String fechaInicial, String fechaFinal){
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<MovimientosTotalesPorConceptoDto>  resultado = transactionRepositoryCustom.findByFechasBetweenGroupByMonth(fechaIni,fechaFin);

        return resultado;
    }
    public List<TransactionDto> buscarTodos(){ //Todo Paginado and Sorting: hacer esto como en PERSONAS API.

        Sort sort = Sort.by(Sort.Direction.DESC, "fecha","id");

        List<Transaction> transactions = transactionRepository.findAllByUsuario(userDetailsServiceImpl.getUsuarioAutenticado(),sort);

        return transactions.stream()
                .map(TransactionDto::new)
                .collect(Collectors.toList());
    }

}
