package com.dogsearch.demo.impl;

import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.repository.CategoryRepo;
import com.dogsearch.demo.service.CategoryService;
import com.dogsearch.demo.util.exception.UtilException;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public Category find(String name) throws Exception {
        return categoryRepo.findByName(name);
    }

    @Override
    public void delete(Category category) throws Exception {
        Category categoryFinded = find(category.getName());
        if (!doesHaveAnId(categoryFinded))
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, categoryException);
        categoryRepo.deleteById(categoryFinded.getId());
    }

    public boolean doesAlreadyExistsInDatabase(Category category) throws Exception {
        Category categoryFinded = find(category.getName());
        return categoryFinded != null;
    }

    public boolean doesHaveAnId(Category category) {
        return category.getId() != null;
    }

    public static List<String> getParams(Category category) throws Exception {
        List<String> params = new ArrayList<>();
        try {
            params.add(category.getName());
        } catch (Exception e) {
            UtilParam.throwAllParamsAreNotFilled(Category.objectNamePtBr);
        }
        return params;
    }
}
