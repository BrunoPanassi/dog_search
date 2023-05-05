package com.dogsearch.demo.service;

import com.dogsearch.demo.model.Category;

import java.util.Optional;

public interface CategoryService {
    Category save(Category category) throws Exception;
    Category find(Long id, String name) throws Exception;
    Category findByName(String name) throws Exception;
    Category delete(Long id) throws Exception;

}
