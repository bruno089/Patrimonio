package com.gabru.Patrimonio.data.repositories;

import com.gabru.Patrimonio.api.dtos.MovimientosTotalesPorConceptoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryCustom {
    @Autowired EntityManager entityManager;

    public List<MovimientosTotalesPorConceptoDto> findByFechasBetweenGroupByMonth( LocalDate fechaInicial, LocalDate fechaFinal){
        String sql = " select " +
                "datename(month, t.date) + '/'+ cast(year(t.date) as varchar(4)) as mes, " +
                "cat.name as concepto , " +
                "sum(t.amount) as importe " +
                "from Transaction t " +
                "inner join Category cat on t.id_category = cat.id " +
                "where t.deleted is null and  t.date between :fechaInicial and :fechaFinal " +
                "OR :fechaInicial is null or :fechaFinal is null " +
                "group by datename(month, t.date) + '/'+ cast(year(t.date) as varchar(4)) , " +
                "'01/'+ cast(month(t.date) as varchar(4)) + '/'+ cast(year(t.date) as varchar(4)), " +
                "cat.name " +
                "order by '01/'+ cast(month(t.date) as varchar(4)) + '/'+ cast(year(t.date) as varchar(4)), " +
                "cat.nombre";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);

        List<Object[]> result = query.getResultList();

        return result.stream().map( registro -> { MovimientosTotalesPorConceptoDto dto = MovimientosTotalesPorConceptoDto.builder()
                .mes(registro[0].toString())
                .concepto(registro[1].toString())
                .importe( Double.valueOf(registro[2].toString()) )
                .build();
            return dto;
        }) .collect(Collectors.toList());
    }
}
