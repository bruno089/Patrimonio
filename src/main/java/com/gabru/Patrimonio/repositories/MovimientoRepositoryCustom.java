package com.gabru.Patrimonio.repositories;

import com.gabru.Patrimonio.dtos.MovimientosTotalesPorConceptoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MovimientoRepositoryCustom {
    @Autowired EntityManager entityManager;

    public List<MovimientosTotalesPorConceptoDto> findByFechasBetweenGroupByMonth( LocalDate fechaInicial, LocalDate fechaFinal){

        String sql = " select " +
                "datename(month, t.fecha) + '/'+ cast(year(t.fecha) as varchar(4)) as mes, " +
                "t2.nombre as concepto , " +
                "sum(t.importe) as importe " +
                "from Movimiento t " +
                "inner join Concepto t2 on t.idConcepto = t2.id " +
                "where t.fecha between :fechaInicial and :fechaFinal " +
                "OR :fechaInicial is null or :fechaFinal is null " +
                "group by datename(month, t.fecha) + '/'+ cast(year(t.fecha) as varchar(4)) , " +
                "'01/'+ cast(month(t.fecha) as varchar(4)) + '/'+ cast(year(t.fecha) as varchar(4)), " +
                "t2.nombre " +
                "order by '01/'+ cast(month(t.fecha) as varchar(4)) + '/'+ cast(year(t.fecha) as varchar(4)), " +
                "t2.nombre";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);


        List<Object[]> result = query.getResultList();

        return result.stream().map( registro -> { MovimientosTotalesPorConceptoDto dto = MovimientosTotalesPorConceptoDto.builder()
                .mes(registro[0].toString())
                .concepto(registro[1].toString())
                .importe( Double.valueOf(registro[2].toString()) )
                .build();
            return  dto;

        }) .collect(Collectors.toList());


    }

}
