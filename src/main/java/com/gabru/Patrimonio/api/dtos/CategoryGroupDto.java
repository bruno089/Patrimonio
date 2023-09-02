package com.gabru.Patrimonio.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabru.Patrimonio.data.entities.CategoryGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.Inet4Address;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class CategoryGroupDto {

    Integer id;
    @NotNull @Size(min = 2, max = 100 , message = "Size between 2 y 50") @NotBlank(message = "name is required")
    String name;



    public CategoryGroupDto ( CategoryGroup categoryGroup ) {
        this.name = categoryGroup.getName();
        this.id = categoryGroup.getId();
    }
}
