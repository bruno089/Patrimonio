package com.gabru.Patrimonio.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabru.Patrimonio.data.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data @NoArgsConstructor
@AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    int id;
    @NotNull @Size(min = 2, max = 100 , message = "Size between 2 and 100") @NotBlank(message = "name is required")
    String name;
    LocalDateTime deleted;
    Integer categoryGroupId;
    String  categoryGroupName;
    public CategoryDto ( Category category ){
        this.id = category.getId();
        this.name = category.getName();
        this.deleted = category.getDeleted();
        this.categoryGroupId = category.getCategoryGroup() == null ? null : category.getCategoryGroup().getId();
        this.categoryGroupName = category.getCategoryGroup() == null ? null : category.getCategoryGroup().getName();
    }
}

