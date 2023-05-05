package com.dogsearch.demo.controller;

import com.dogsearch.demo.dto.category.CategoryCreateDTO;
import com.dogsearch.demo.impl.CategoryServiceImpl;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @GetMapping("")
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "0") Long id,
                              @RequestParam(name = "name", defaultValue = "_default_", required = false) String name) {
        try {
            return ResponseEntity.ok().body(categoryService.find(id, name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody CategoryCreateDTO categoryBody) {
        try {
            Category category = new Category(categoryBody.getName());
            UtilParam.checkIfAllParamsAreFilled(categoryService.getParams(category), Category.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/category/save").toUriString());
            return ResponseEntity.created(location).body(categoryService.save(category));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PutMapping("/save/{id}")
    public ResponseEntity update(@RequestBody CategoryCreateDTO categoryBody, @PathVariable("id") Long id) {
        try {
            Category category = new Category(id, categoryBody.getName());
            UtilParam.checkIfAllParamsAreFilled(categoryService.getParams(category), Category.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/category/save/".concat(String.valueOf(id))).toUriString());
            return ResponseEntity.created(location).body(categoryService.save(category));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/".concat(String.valueOf(id))).toUriString());
            return ResponseEntity.ok().body(categoryService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}
