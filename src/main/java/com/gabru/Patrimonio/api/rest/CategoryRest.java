package com.gabru.Patrimonio.api.rest;

import com.gabru.Patrimonio.domain.services.CategoryService;
import com.gabru.Patrimonio.api.dtos.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("hasRole('CUSTOMER')")
@RestController
@RequestMapping(CategoryRest.CATEGORIES)
@AllArgsConstructor
public class CategoryRest {
    public static final String CATEGORIES = "/categories";
    public static final String ID = "/{id}";
    public static final String CATEGORY_NAME = "/name";
    CategoryService categoryService;
    @PostMapping
    public CategoryDto create ( @Valid @RequestBody CategoryDto categoryDto ){
        return categoryService.createCategory(categoryDto);
    }
    @GetMapping(ID)
    public CategoryDto read ( @PathVariable Integer id){
        return categoryService.read(id);
    }
    @PutMapping(ID)
    public CategoryDto update ( @PathVariable Integer id, @Valid @RequestBody CategoryDto categoryDto ){
        return categoryService.update(id, categoryDto);
    }
    @DeleteMapping(ID)
    public void delete ( @PathVariable int id){
        categoryService.delete(id);
    }
    @GetMapping    @Operation(summary = "readAll", security = @SecurityRequirement(name = "JWT Token"))
    public List<CategoryDto> readAll (){
        return categoryService.readAll();
    }
    @GetMapping(CATEGORY_NAME)
    public List<CategoryDto> findByName ( @RequestParam String name ){
        return categoryService.readAllByName(name);
    }
}
