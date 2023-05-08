package com.dogsearch.demo.service;

import com.dogsearch.demo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category) throws Exception;
    List<Category> find(Long id, String name) throws Exception;
    Category findById(Long id) throws Exception;
    List<Category> findByName(String name) throws Exception;
    Category delete(Long id) throws Exception;

}
