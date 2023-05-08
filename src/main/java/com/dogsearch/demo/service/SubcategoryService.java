package com.dogsearch.demo.service;

import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.SubCategory;

import java.util.List;

public interface SubcategoryService {
    SubCategory save(SubCategory subCategory) throws Exception;
    List<SubCategory> find(Long id, String name) throws Exception;
    List<SubCategory> findByName(String name) throws Exception;
    SubCategory delete(Long id) throws Exception;
}
