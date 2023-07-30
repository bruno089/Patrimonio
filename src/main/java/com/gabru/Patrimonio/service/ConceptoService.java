package com.gabru.Patrimonio.service;

import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
@AllArgsConstructor
public class ConceptoService {

    public static final boolean CONCEPTO_TIPO_DEFAULT = false;
    ConceptoRepository conceptoRepository;
    public Concepto getConcepto( String conceptoDescripcion){ //Todo try catch?
        /** Concepto  - Servicio
         *
         * El manejo de concepto tiene q estar nucleado en un solo lugar (Principio de Unica Responsabilidad)
         * El servicio se debe de encargar de devolver el concepto en base a su descripcion. Debe poder distinguir entre minusculas y mayusculas Comida COMIDA
         *          *  Ademas si el concepto no existe en BD se debe guardar este concepto
         * Cuidado:
         * - Case sensitive                 --
         * - En plural y/o en singular      xx
         * - Con muchas llamadas a BD       --
         * *
         * */

        //Limpiezas del Concepto, segun se vayan necesitando
        conceptoDescripcion = conceptoDescripcion.trim();
        String conceptoDescripcionUpper = conceptoDescripcion.toUpperCase();

        Map<String,Concepto> conceptosEnBDMap =  new HashMap<>();
        conceptoRepository
                .findAll()
                .forEach( concepto -> conceptosEnBDMap.put(concepto.getNombre().toUpperCase(),concepto) );

        Concepto conceptoResultado;

        if ( conceptosEnBDMap.containsKey(conceptoDescripcionUpper) ){
            conceptoResultado = conceptosEnBDMap.get(conceptoDescripcionUpper);
        }else{
            conceptoResultado =  conceptoRepository.save(
                    Concepto.builder().nombre(conceptoDescripcion).ingreso(CONCEPTO_TIPO_DEFAULT).build());
        }

        return conceptoResultado;
    }
}
