package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.business_controllers.LecturaArchivos.LectorArchivosContext;
import com.gabru.Patrimonio.business_controllers.LecturaArchivos.LectorTipo;
import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.dtos.MovimientosTotalesPorConceptoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.entities.Movimiento;


import com.gabru.Patrimonio.entities.Usuario;
import com.gabru.Patrimonio.exceptions.ConflictException;
import com.gabru.Patrimonio.exceptions.NotFoundException;

import com.gabru.Patrimonio.repositories.MovimientoRepository;
import com.gabru.Patrimonio.repositories.MovimientoRepositoryCustom;
import com.gabru.Patrimonio.service.ConceptoService;
import com.gabru.Patrimonio.business_services.FechaConverterService;
import com.gabru.Patrimonio.service.UsuarioService;
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

import static com.gabru.Patrimonio.business_services.FechaConverterService.stringtoLocalDate;

@Controller
@AllArgsConstructor
public class MovimientoController {
    MovimientoRepository movimientoRepository;
    MovimientoRepositoryCustom movimientoRepositoryCustom;
    ConceptoService conceptoService;
    UsuarioService usuarioService;
    public void CsvAMovimientoDtoList ( MultipartFile archivo, String tipoImportacion ){

        LectorArchivosContext lectorArchivosContext = new LectorArchivosContext(LectorTipo.CSV );

        List<MovimientoDto> movimientoDtos =  lectorArchivosContext.ejecutar(archivo);

        //this.agregar(movimientoDtos); //Todo check this

        movimientoDtos.forEach( movimientoDto -> this.agregar( movimientoDto));
    }
    public MovimientoDto agregar( MovimientoDto movimientoDto) {

        LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Concepto newConcepto =  conceptoService.getConcepto(movimientoDto.getConceptoDescripcion());

        Movimiento movimiento = Movimiento.builder()
                .observacion(movimientoDto.getObservacion())
                .importe(movimientoDto.getImporte())
                .fecha(fecha)
                .alta(LocalDateTime.now())
                .concepto(newConcepto)
                .usuario(usuarioService.getUsuarioAutenticado())
                .build();

        movimientoRepository.save(movimiento);

        return new MovimientoDto(movimiento);
    }
    public MovimientoDto agregar(List<MovimientoDto> movimientoDtoList) {
        try {
            // Obtengo el usuario autenticado
            Usuario usuarioAutenticado = usuarioService.getUsuarioAutenticado();

            // Lista para almacenar los nuevos movimientos creados
            List<Movimiento> nuevosMovimientos = new ArrayList<>();

            // Recorro la lista de MovimientoDto recibida
            for (MovimientoDto movimientoDto : movimientoDtoList) {
                // Parseo la fecha del movimientoDto
                LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                // Obtengo el Concepto correspondiente
                Concepto concepto = conceptoService.getConcepto(movimientoDto.getConceptoDescripcion());

                // Creo el nuevo Movimiento
                Movimiento movimiento = Movimiento.builder()
                        .observacion(movimientoDto.getObservacion())
                        .importe(movimientoDto.getImporte())
                        .fecha(fecha)
                        .alta(LocalDateTime.now())
                        .concepto(concepto)
                        .usuario(usuarioAutenticado)
                        .build();

                // Agrego el nuevo Movimiento a la lista de nuevosMovimientos
                nuevosMovimientos.add(movimiento);
            }

            // Guardo todos los nuevos movimientos en la base de datos
            movimientoRepository.saveAll(nuevosMovimientos);

            // Retorno el primer MovimientoDto creado, podrías ajustar esto según tus necesidades
            return new MovimientoDto(nuevosMovimientos.get(0));

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

        Movimiento movimiento = movimientoRepository
                .findByIdAndUsuario(movimientoId,usuarioService.getUsuarioAutenticado())
                .orElseThrow(()-> new NotFoundException("No encontrado movimiento: " + movimientoId ));

        movimientoRepository.delete(movimiento);
    }
    public MovimientoDto actualizar(int id, MovimientoDto movimientoDto) {
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(()-> new NotFoundException("No se encuentra el movimiento con ID: " + id));

        Concepto elConcepto =  conceptoService.getConcepto(movimientoDto.getConceptoDescripcion());

        movimiento.setConcepto(elConcepto);
        movimiento.setFecha(FechaConverterService.stringtoLocalDate(movimientoDto.getFecha(), "dd/MM/yyyy"));
        movimiento.setImporte(movimientoDto.getImporte());
        movimiento.setObservacion(movimientoDto.getObservacion());
        movimientoRepository.save(movimiento);

        return new MovimientoDto(movimiento);
    }
    public List<MovimientoDto> buscarMovimientosPorFecha(String fechaInicial, String fechaFinal) {
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<MovimientoDto> movimientos = movimientoRepository.findAllByFechaBetweenOrderByFecha(fechaIni,fechaFin);
       // List<MovimientoDto> movimientos = movimientoRepository.findAllByFechaBetweenAndUsuarioOrderByFecha(fechaIni,fechaFin,usuarioService.getUsuarioAutenticado());

        return movimientos;
    }
    public List<MovimientosTotalesPorConceptoDto> totalizador( String fechaInicial, String fechaFinal){
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<MovimientosTotalesPorConceptoDto>  resultado = movimientoRepositoryCustom.findByFechasBetweenGroupByMonth(fechaIni,fechaFin);

        return resultado;
    }
    public List<MovimientoDto> buscarTodos(){ //Todo Paginado and Sorting: hacer esto como en PERSONAS API.

        Sort sort = Sort.by(Sort.Direction.DESC, "fecha","id");

        List<Movimiento> movimientos = movimientoRepository.findAllByUsuario(usuarioService.getUsuarioAutenticado(),sort);

        return movimientos.stream()
                .map(MovimientoDto::new)
                .collect(Collectors.toList());
    }
    public MovimientoDto buscar ( Integer movimientoId ) {

        Movimiento movimiento = movimientoRepository
                .findById(movimientoId)
                .orElseThrow(() -> new NotFoundException("No encontrado id:"+ movimientoId));

        return  new MovimientoDto(movimiento);
    }
}
