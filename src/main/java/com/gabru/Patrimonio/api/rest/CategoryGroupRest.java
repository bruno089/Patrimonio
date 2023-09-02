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
    public static final String CATEGORY_GROUP_ID = "/{categoryGroupId}";

    @PostMapping
    public CategoryGroupDto create ( @Valid @RequestBody CategoryGroupDto categoryGroupDto ){
        return categoryGroupService.create(categoryGroupDto);
    }
    @GetMapping(CATEGORY_GROUP_ID)
    public CategoryGroupDto read ( @PathVariable Integer categoryGroupId){
        return categoryGroupService.read(categoryGroupId);
    }
    @GetMapping()
    public Stream<CategoryGroupDto> readAll (){
        return categoryGroupService.readAll();
    }
    @PutMapping(CATEGORY_GROUP_ID)
    public CategoryGroupDto update ( @PathVariable Integer categoryGroupId, @Valid @RequestBody CategoryGroupDto categoryGroupDto ){
        return  categoryGroupService.update(categoryGroupId, categoryGroupDto);
    }
    @DeleteMapping(CATEGORY_GROUP_ID)
    public void delete ( @PathVariable int categoryGroupId){
        categoryGroupService.delete(categoryGroupId);
    }

}
