package com.dogsearch.demo.controller;

import com.dogsearch.demo.dto.category.CategorySaveDTO;
import com.dogsearch.demo.dto.subCategory.SubCategorySaveDTO;
import com.dogsearch.demo.impl.CategoryServiceImpl;
import com.dogsearch.demo.impl.SubCategoryServiceImpl;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.SubCategory;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/sub-category")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class SubCategoryController {
    private final CategoryServiceImpl categoryService;
    private final SubCategoryServiceImpl subCategoryService;

    @GetMapping()
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "0") Long id,
                              @RequestParam(name = "name", defaultValue = UtilParam.DEFAULT_STRING_PARAM_TO_REPO, required = false) String name) {
        try {
            return ResponseEntity.ok().body(subCategoryService.find(id, name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @GetMapping("/category")
    public ResponseEntity getByCategory(@RequestParam(name = "id", defaultValue = "0") Long id,
                              @RequestParam(name = "name", defaultValue = UtilParam.DEFAULT_STRING_PARAM_TO_REPO, required = false) String name) {
        try {
            return ResponseEntity.ok().body(subCategoryService.findByCategory(id, name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody SubCategorySaveDTO subCategoryBody) {
        try {
            Category category = categoryService.findById(subCategoryBody.getCategoryId());
            SubCategory subCategory = new SubCategory(subCategoryBody.getName(), category);
            UtilParam.checkIfAllParamsAreFilled(subCategoryService.getParams(subCategory), SubCategory.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/sub-category/save").toUriString());
            return ResponseEntity.created(location).body(subCategoryService.save(subCategory));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PutMapping("/save/{id}")
    public ResponseEntity update(@RequestBody SubCategorySaveDTO subCategoryBody, @PathVariable("id") Long id) {
        try {
            Category category = categoryService.findById(subCategoryBody.getCategoryId());
            SubCategory subCategory = new SubCategory(id, subCategoryBody.getName(), category);
            UtilParam.checkIfAllParamsAreFilled(subCategoryService.getParams(subCategory), SubCategory.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/sub-category/save/".concat(String.valueOf(id))).toUriString());
            return ResponseEntity.created(location).body(subCategoryService.save(subCategory));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(subCategoryService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}
