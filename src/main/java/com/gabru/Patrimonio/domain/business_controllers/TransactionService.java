package com.gabru.Patrimonio.domain.business_controllers;

import com.gabru.Patrimonio.domain.business_controllers.LecturaArchivos.LectorArchivosContext;
import com.gabru.Patrimonio.domain.business_controllers.LecturaArchivos.LectorTipo;
import com.gabru.Patrimonio.api.dtos.TransactionDto;
import com.gabru.Patrimonio.api.dtos.MovimientosTotalesPorConceptoDto;
import com.gabru.Patrimonio.data.entities.Concepto;
import com.gabru.Patrimonio.data.entities.Transaction;


import com.gabru.Patrimonio.data.entities.Usuario;
import com.gabru.Patrimonio.domain.exceptions.ConflictException;
import com.gabru.Patrimonio.domain.exceptions.NotFoundException;

import com.gabru.Patrimonio.data.repositories.MovimientoRepository;
import com.gabru.Patrimonio.data.repositories.MovimientoRepositoryCustom;
import com.gabru.Patrimonio.domain.service.ConceptoService;
import com.gabru.Patrimonio.utils.business_services.FechaConverterService;
import com.gabru.Patrimonio.domain.service.UsuarioService;
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
public class MovimientoController {
    MovimientoRepository movimientoRepository;
    MovimientoRepositoryCustom movimientoRepositoryCustom;
    ConceptoService conceptoService;
    UsuarioService usuarioService;
    public void CsvAMovimientoDtoList ( MultipartFile archivo, String tipoImportacion ){

        LectorArchivosContext lectorArchivosContext = new LectorArchivosContext(LectorTipo.CSV );

        List<TransactionDto> transactionDtos =  lectorArchivosContext.ejecutar(archivo);

        //this.agregar(movimientoDtos); //Todo check this

        transactionDtos.forEach(movimientoDto -> this.agregar( movimientoDto));
    }
    public TransactionDto agregar( TransactionDto transactionDto ) {

        LocalDate fecha = LocalDate.parse(transactionDto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Concepto newConcepto =  conceptoService.getConcepto(transactionDto.getConceptoDescripcion());

        Transaction transaction = Transaction.builder()
                .observacion(transactionDto.getObservacion())
                .importe(transactionDto.getImporte())
                .fecha(fecha)
                .alta(LocalDateTime.now())
                .concepto(newConcepto)
                .usuario(usuarioService.getUsuarioAutenticado())
                .build();

        movimientoRepository.save(transaction);

        return new TransactionDto(transaction);
    }
    public TransactionDto agregar( List<TransactionDto> transactionDtoList ) {
        try {
            // Obtengo el usuario autenticado
            Usuario usuarioAutenticado = usuarioService.getUsuarioAutenticado();

            // Lista para almacenar los nuevos movimientos creados
            List<Transaction> nuevosTransactions = new ArrayList<>();

            // Recorro la lista de MovimientoDto recibida
            for (TransactionDto transactionDto : transactionDtoList) {
                // Parseo la fecha del movimientoDto
                LocalDate fecha = LocalDate.parse(transactionDto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                // Obtengo el Concepto correspondiente
                Concepto concepto = conceptoService.getConcepto(transactionDto.getConceptoDescripcion());

                // Creo el nuevo Movimiento
                Transaction transaction = Transaction.builder()
                        .observacion(transactionDto.getObservacion())
                        .importe(transactionDto.getImporte())
                        .fecha(fecha)
                        .alta(LocalDateTime.now())
                        .concepto(concepto)
                        .usuario(usuarioAutenticado)
                        .build();

                // Agrego el nuevo Movimiento a la lista de nuevosMovimientos
                nuevosTransactions.add(transaction);
            }

            // Guardo todos los nuevos movimientos en la base de datos
            movimientoRepository.saveAll(nuevosTransactions);

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
    public void borrar(int movimientoId) {

        Transaction transaction = movimientoRepository
                .findByIdAndUsuario(movimientoId,usuarioService.getUsuarioAutenticado())
                .orElseThrow(()-> new NotFoundException("No encontrado movimiento: " + movimientoId ));

        movimientoRepository.delete(transaction);
    }
    public TransactionDto actualizar( int id, TransactionDto transactionDto ) {
        Transaction transaction = movimientoRepository.findById(id).orElseThrow(()-> new NotFoundException("No se encuentra el movimiento con ID: " + id));

        Concepto elConcepto =  conceptoService.getConcepto(transactionDto.getConceptoDescripcion());

        transaction.setConcepto(elConcepto);
        transaction.setFecha(FechaConverterService.stringtoLocalDate(transactionDto.getFecha(), "dd/MM/yyyy"));
        transaction.setImporte(transactionDto.getImporte());
        transaction.setObservacion(transactionDto.getObservacion());
        movimientoRepository.save(transaction);

        return new TransactionDto(transaction);
    }
    public List<TransactionDto> buscarMovimientosPorFecha( String fechaInicial, String fechaFinal) {
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<TransactionDto> movimientos = movimientoRepository.findAllByFechaBetweenOrderByFecha(fechaIni,fechaFin);
       // List<MovimientoDto> movimientos = movimientoRepository.findAllByFechaBetweenAndUsuarioOrderByFecha(fechaIni,fechaFin,usuarioService.getUsuarioAutenticado());

        return movimientos;
    }
    public List<MovimientosTotalesPorConceptoDto> totalizador( String fechaInicial, String fechaFinal){
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<MovimientosTotalesPorConceptoDto>  resultado = movimientoRepositoryCustom.findByFechasBetweenGroupByMonth(fechaIni,fechaFin);

        return resultado;
    }
    public List<TransactionDto> buscarTodos(){ //Todo Paginado and Sorting: hacer esto como en PERSONAS API.

        Sort sort = Sort.by(Sort.Direction.DESC, "fecha","id");

        List<Transaction> transactions = movimientoRepository.findAllByUsuario(usuarioService.getUsuarioAutenticado(),sort);

        return transactions.stream()
                .map(TransactionDto::new)
                .collect(Collectors.toList());
    }
    public TransactionDto buscar ( Integer movimientoId ) {

        Transaction transaction = movimientoRepository
                .findById(movimientoId)
                .orElseThrow(() -> new NotFoundException("No encontrado id:"+ movimientoId));

        return  new TransactionDto(transaction);
    }
}
