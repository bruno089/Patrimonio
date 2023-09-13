package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.domain.services.LecturaArchivos.LectorArchivosContext;
import com.gabru.Patrimonio.domain.services.LecturaArchivos.LectorTipo;
import com.gabru.Patrimonio.api.dtos.TransactionDto;
import com.gabru.Patrimonio.api.dtos.MovimientosTotalesPorConceptoDto;
import com.gabru.Patrimonio.data.entities.Transaction;


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

       Transaction transaction = transactionRepository.save(this.builderTransaction(transactionDto));

       return new TransactionDto(transaction);
    }
    public void create ( List<TransactionDto> transactionDtoList ) {
        //Todo report quantity of transactions created vs quantity of transactions received
        try {
            List<Transaction> transactions = new ArrayList<>();
            for (TransactionDto transactionDto : transactionDtoList) {
                transactions.add(this.builderTransaction(transactionDto));
            }
            transactionRepository.saveAll(transactions);

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
    public TransactionDto read ( Integer id ) {

        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Transaction id: " + id));

        return  new TransactionDto(transaction);
    }
    public TransactionDto update ( int id, TransactionDto transactionDto ) {

        Transaction transaction = transactionRepository
                .findByIdAndUser(id, userDetailsServiceImpl.getUserAuth())
                .orElseThrow(()-> new NotFoundException("Not found Transaction id: " + id));

        if ( transactionDto.getCategoryName()  != null &&
             transactionDto.getCategoryName()  != transaction.getCategory().getName() ){

            Category  newCategory =  categoryService.findByNameOrSaveCategory(transactionDto.getCategory());

            transaction.setCategory(newCategory);
        }
        //Todo si tiene valor que modifique, sino no? o permito que limpie datos?
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(FechaConverterService.stringtoLocalDate(transactionDto.getDate(), "dd/MM/yyyy"));
        transaction.setDetail(transactionDto.getDetail());

        transactionRepository.save(transaction);

        return new TransactionDto(transaction);
    }
    public void delete ( int id ){

        Transaction transaction = transactionRepository
                .findByIdAndUser(id, userDetailsServiceImpl.getUserAuth())
                .orElseThrow(()-> new NotFoundException("Not found Transaction id: " + id));

        transactionRepository.delete(transaction);
    }
    public void createTransactionsFromCSV ( MultipartFile archivo, String tipoImportacion ){

        LectorArchivosContext lectorArchivosContext = new LectorArchivosContext(LectorTipo.CSV );

        List<TransactionDto> transactionDtos =  lectorArchivosContext.read(archivo);

        this.create(transactionDtos);
    }

    public List<TransactionDto> readAllBetweenDates ( String fechaInicial, String fechaFinal) {
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<TransactionDto> movimientos = transactionRepository.findAllByDateBetweenOrderByDate(fechaIni,fechaFin);
       // List<MovimientoDto> movimientos = movimientoRepository.findAllByFechaBetweenAndUsuarioOrderByFecha(fechaIni,fechaFin,usuarioService.getUsuarioAutenticado());

        return movimientos;
    }
    public List<MovimientosTotalesPorConceptoDto> totalizador( String fechaInicial, String fechaFinal){
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<MovimientosTotalesPorConceptoDto>  resultado = transactionRepositoryCustom.findByFechasBetweenGroupByMonth(fechaIni,fechaFin);

        return resultado;
    }
    public List<TransactionDto> readAll (){ //Todo Paginado and Sorting: hacer esto como en PERSONAS API.

        Sort sort = Sort.by(Sort.Direction.DESC, "date","id");

        List<Transaction> transactions = transactionRepository.findAllByUser(userDetailsServiceImpl.getUserAuth(),sort);

        return transactions.stream()
                .map(TransactionDto::new)
                .collect(Collectors.toList());
    }

    //encapsulathe create logic in a method
    private Transaction builderTransaction ( TransactionDto transactionDto ) {

        LocalDate date = LocalDate.parse(transactionDto.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Category category =  categoryService.findByNameOrSaveCategory(transactionDto.getCategory());

        Transaction transaction = Transaction.builder()
                .detail(transactionDto.getDetail())
                .amount(transactionDto.getAmount())
                .date(date)
                .dateCreation(LocalDateTime.now())
                .category(category)
                .user(userDetailsServiceImpl.getUserAuth())
                .build();

        return  transaction;
    }

}
