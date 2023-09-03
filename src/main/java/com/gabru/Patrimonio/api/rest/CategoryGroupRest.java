package com.gabru.Patrimonio.api.rest;

import com.gabru.Patrimonio.api.dtos.CategoryGroupDto;
import com.gabru.Patrimonio.domain.services.CategoryGroupService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@RequestMapping(CategoryGroupRest.CATEGORY_GROUP)
@RestController
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class CategoryGroupRest {
     CategoryGroupService categoryGroupService;
    public static final String CATEGORY_GROUP = "/categoryGroup";
    public static final String ID = "/{id}";

    @PostMapping
    public CategoryGroupDto create ( @Valid @RequestBody CategoryGroupDto categoryGroupDto ){
        return categoryGroupService.create(categoryGroupDto);
    }
    @GetMapping(ID)
    public CategoryGroupDto read ( @PathVariable Integer id ){
        return categoryGroupService.read(id);
    }
    @GetMapping()
    public Stream<CategoryGroupDto> readAll (){
        return categoryGroupService.readAll();
    }
    @PutMapping(ID)
    public CategoryGroupDto update ( @PathVariable Integer id, @Valid @RequestBody CategoryGroupDto categoryGroupDto ){
        return  categoryGroupService.update(id, categoryGroupDto);
    }
    @DeleteMapping(ID)
    public void delete ( @PathVariable int id ){
        categoryGroupService.delete(id);
    }

}
