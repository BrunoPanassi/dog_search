package com.dogsearch.demo.impl;

import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.SubCategory;
import com.dogsearch.demo.repository.SubCategoryRepo;
import com.dogsearch.demo.service.SubcategoryService;
import com.dogsearch.demo.util.exception.UtilException;
import com.dogsearch.demo.util.param.UtilParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubcategoryService {

    @Autowired
    private SubCategoryRepo subCategoryRepo;
    public static final String[] subCategoryException = {SubCategory.objectNamePtBr};
    @Override
    public SubCategory save(SubCategory subCategory) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getParams(subCategory), SubCategory.objectNamePtBr);
        verifyIfDoensHaveAndIdButAlreadyExistsInDatabase(subCategory);
        verifyIfHaveAnIdButDoensExistsInDatabase(subCategory);
        return subCategoryRepo.save(subCategory);
    }

    public void verifyIfDoensHaveAndIdButAlreadyExistsInDatabase(SubCategory subCategory) throws Exception {
        if (!doesHaveAnId(subCategory) && doesAlreadyExistsInDatabaseByName(subCategory))
            UtilException.throwWithMessageBuilder(UtilException.ALREADY_EXISTS_WITH_PARAM, subCategoryException);
    }

    private void verifyIfHaveAnIdButDoensExistsInDatabase(SubCategory subCategory) throws Exception {
        if (doesHaveAnId(subCategory) && !doesAlreadyExistsInDatabaseById(subCategory))
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, subCategoryException);
    }

    @Override
    public List<SubCategory> find(Long id, String name) throws Exception {
        if (id == null || name == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DONT_FILLED, subCategoryException);
        return ifCannotFindThrowEitherReturnSubCategoryBy(id, name);
    }

    public List<SubCategory> ifCannotFindThrowEitherReturnSubCategoryBy(Long id, String name) throws Exception {
        List<SubCategory> subCategoryFounded = subCategoryRepo.findByIdAndName(id, name);
        if (subCategoryFounded == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, subCategoryException);
        return subCategoryFounded;
    }

    @Override
    public List<SubCategory> findByName(String name) throws Exception {
        if (name == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DONT_FILLED, subCategoryException);
        return ifCannotFindThrowEitherReturnSubCategoryBy(UtilParam.DEFAULT_LONG_PARAM_TO_REPO, name);
    }

    public List<SubCategory> findByCategory(Long id, String name) throws Exception {
        if (id == null || name == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DONT_FILLED, subCategoryException);
        List<SubCategory> subCategoryFounded = subCategoryRepo.findByCategoryIdAndName(id, name);
        if (subCategoryFounded == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, subCategoryException);
        return subCategoryFounded;
    }

    @Override
    public SubCategory delete(Long id) throws Exception {
        Optional<SubCategory> subCategoryFinded = subCategoryRepo.findById(id);
        if (!subCategoryFinded.isPresent())
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, subCategoryException);
        subCategoryRepo.deleteById(id);
        return subCategoryFinded.get();
    }

    public boolean doesHaveAnId(SubCategory subCategory) {
        return subCategory.getId() != null;
    }

    public boolean doesAlreadyExistsInDatabaseById(SubCategory subCategory) {
        Optional<SubCategory> subCategoryFinded = subCategoryRepo.findById(subCategory.getId());
        return subCategoryFinded.isPresent();
    }

    public boolean doesAlreadyExistsInDatabaseByName(SubCategory subCategory) {
        SubCategory subCategoryFinded = subCategoryRepo.findByName(subCategory.getName());
        return subCategoryFinded != null;
    }

    public List<String> getParams(SubCategory subCategory) throws Exception {
        List<String> params = new ArrayList<>();
        try {
            params.add(subCategory.getName());
            params.add(subCategory.getCategory().getName());
        } catch (Exception e) {
            UtilParam.throwAllParamsAreNotFilled(SubCategory.objectNamePtBr);
        }
        return params;
    }
}
