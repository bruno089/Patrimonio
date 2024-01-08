package com.gabru.Patrimonio.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabru.Patrimonio.data.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionDto {
    Integer id;
    Double amount;
    String detail;
    String date;
    String dateCreation;


/*    String  categoryName;
    Integer categoryId;
    Integer categoryGroupId;
    String  categoryGroupName;*/

    CategoryDto category;
    public TransactionDto ( Transaction transaction ) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.detail = transaction.getDetail();
        this.date = transaction.getDate() == null ? null : transaction.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       /* this.categoryName =  transaction.getCategory() == null ? null : transaction.getCategory().getName();
        this.categoryId = transaction.getCategory() == null ? null : transaction.getCategory().getId();*/
        this.dateCreation = transaction.getDateCreation().toString();

        if ( transaction.getCategory() != null ) { category = new CategoryDto(transaction.getCategory()); }

       /* if ( transaction.getCategory() != null && transaction.getCategory().getCategoryGroup() != null ) {
            this.categoryGroupId = transaction.getCategory().getCategoryGroup() == null ? null : transaction.getCategory().getCategoryGroup().getId();
            this.categoryGroupName = transaction.getCategory().getCategoryGroup() == null ? null : transaction.getCategory().getCategoryGroup().getName();
        }*/




    }
}
