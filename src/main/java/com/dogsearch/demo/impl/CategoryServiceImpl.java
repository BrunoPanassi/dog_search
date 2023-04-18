package com.dogsearch.demo.impl;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.Person;
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

    @Override
    public Category save(Category category) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getParams(category));
        if (!doesHaveAnId(category) && doesAlreadyExistsInDatabase(category)) {
            List<String> params = new ArrayList<>() {{add(Category.objectNamePtBr);}};
            UtilException.throwDefault(UtilException.exceptionMessageBuilder(UtilException.ALREADY_EXISTS_WITH_PARAM, params));
        }
        return categoryRepo.save(category);
    }

    @Override
    public Category find(String name) throws Exception {
        return categoryRepo.findByName(name);
    }

    @Override
    public void delete(Category category) throws Exception {
        Category categoryFinded = find(category.getName());
        if (categoryFinded.getId() == null) {
            List<String> params = new ArrayList<>() {{add(Category.objectNamePtBr);}};
            UtilException.throwDefault(UtilException.exceptionMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, params));
        }
        categoryRepo.deleteById(categoryFinded.getId());
    }

    public boolean doesAlreadyExistsInDatabase(Category category) throws Exception {
        Category categoryFinded = find(category.getName());
        return categoryFinded != null;
    }

    public boolean doesHaveAnId(Category category) {
        return category.getId() != null;
    }

    public static String[] getParams(Category category) {
        String[] params = {
                category.getName()
        };
        return params;
    }
}
