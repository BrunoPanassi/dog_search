package com.dogsearch.demo.service;

import com.dogsearch.demo.model.Category;

public interface CategoryService {
    Category save(Category category) throws Exception;
    Category find(String name) throws Exception;
    void delete(Category category) throws Exception;

}
