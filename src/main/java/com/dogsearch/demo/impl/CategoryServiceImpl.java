package com.dogsearch.demo.impl;

import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.repository.CategoryRepo;
import com.dogsearch.demo.service.CategoryService;
import com.dogsearch.demo.util.exception.UtilException;
import com.dogsearch.demo.util.param.UtilParam;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    public static final String[] categoryException = {Category.objectNamePtBr};

    @Override
    public Category save(Category category) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getParams(category), Category.objectNamePtBr);
        if (!doesHaveAnId(category) && doesAlreadyExistsInDatabase(category))
            UtilException.throwWithMessageBuilder(UtilException.ALREADY_EXISTS_WITH_PARAM, categoryException);
        return categoryRepo.save(category);
    }

    @Override
    public Category find(Long id, String name) throws Exception {
        if (id == null || name == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DONT_FILLED, categoryException);
        Category categoryFounded = categoryRepo.findByIdAndName(id, name);
        if (categoryFounded == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, categoryException);
        return categoryFounded;
    }

    @Override
    public Category findByName(String name) throws Exception {
        if (name == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DONT_FILLED, categoryException);
        Category categoryFounded = categoryRepo.findByName(name);
        if (categoryFounded == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, categoryException);
        return categoryFounded;
    }

    @Override
    public Category delete(Long id) throws Exception {
        Optional<Category> categoryFinded = categoryRepo.findById(id);
        if (!categoryFinded.isPresent())
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, categoryException);
        categoryRepo.deleteById(id);
        return categoryFinded.get();
    }

    public boolean doesAlreadyExistsInDatabase(Category category) throws Exception {
        Category categoryFinded = findByName(category.getName());
        return categoryFinded != null;
    }

    public boolean doesHaveAnId(Category category) {
        return category.getId() != null;
    }

    public List<String> getParams(Category category) throws Exception {
        List<String> params = new ArrayList<>();
        try {
            params.add(category.getName());
        } catch (Exception e) {
            UtilParam.throwAllParamsAreNotFilled(Category.objectNamePtBr);
        }
        return params;
    }
}
