package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.api.dtos.CategoryGroupDto;
import com.gabru.Patrimonio.data.entities.CategoryGroup;
import com.gabru.Patrimonio.data.repositories.CategoryGroupRepository;
import com.gabru.Patrimonio.domain.exceptions.ConflictException;
import com.gabru.Patrimonio.domain.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CategoryGroupService {
    CategoryGroupRepository categoryGroupRepository;
    UserDetailsServiceImpl userDetailsServiceImpl;

    public CategoryGroupDto create ( CategoryGroupDto categoryGroupDto ){

        if ( categoryGroupRepository.findByNameAndUser(categoryGroupDto.getName(),userDetailsServiceImpl.getUserAuth()).isPresent()){
            throw new ConflictException("Already exist Category Group: " + categoryGroupDto.getName());
        }

        CategoryGroup categoryGroup =  CategoryGroup.builder()
                .name(categoryGroupDto.getName())
                .user(userDetailsServiceImpl.getUserAuth())
                .build();

        categoryGroupRepository.save(categoryGroup);

        return new CategoryGroupDto(categoryGroup);
    }
    public CategoryGroupDto read ( int id){
        Optional<CategoryGroup> categoryGroup = categoryGroupRepository.findByIdAndUser(id,userDetailsServiceImpl.getUserAuth());

        if (! categoryGroup.isPresent()) { throw new NotFoundException("Not found Category Group ID: " + id ); }

        return new CategoryGroupDto(categoryGroup.get());
    }

    public CategoryGroupDto update ( Integer id, CategoryGroupDto CategoryGroupDto ) {

        CategoryGroup categoryGroup = categoryGroupRepository
                .findByIdAndUser(id,userDetailsServiceImpl.getUserAuth())
                .orElseThrow(() -> new NotFoundException("Not found Category Group ID: : " + id ));

        categoryGroup.setName(CategoryGroupDto.getName());

        return new CategoryGroupDto(categoryGroupRepository.save(categoryGroup));
    }
    public void delete ( int id){
        if ( categoryGroupRepository.findByIdAndUser(id, userDetailsServiceImpl.getUserAuth()).isPresent()){
            categoryGroupRepository.deleteById(id);
        }
    }


    public Stream<CategoryGroupDto> readAll () {
        return categoryGroupRepository.findAllByUser(userDetailsServiceImpl.getUserAuth())
                .stream()
                .map(CategoryGroupDto::new);
    }
}
