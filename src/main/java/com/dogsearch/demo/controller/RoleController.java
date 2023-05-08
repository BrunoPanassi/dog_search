package com.dogsearch.demo.controller;

import com.dogsearch.demo.dto.category.CategorySaveDTO;
import com.dogsearch.demo.dto.role.RoleSaveDTO;
import com.dogsearch.demo.impl.RoleServiceImpl;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.Role;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;

    @GetMapping()
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "0") Long id,
                              @RequestParam(name = "name", defaultValue = UtilParam.DEFAULT_STRING_PARAM_TO_REPO, required = false) String name) {
        try {
            return ResponseEntity.ok().body(roleService.find(id, name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody RoleSaveDTO roleBody) {
        try {
            Role category = new Role(roleBody.getName());
            UtilParam.checkIfAllParamsAreFilled(roleService.getParams(category), Role.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString());
            return ResponseEntity.created(location).body(roleService.save(category));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PutMapping("/save/{id}")
    public ResponseEntity update(@RequestBody RoleSaveDTO roleBody, @PathVariable("id") Long id) {
        try {
            Role category = new Role(roleBody.getName());
            UtilParam.checkIfAllParamsAreFilled(roleService.getParams(category), Role.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save/".concat(String.valueOf(id))).toUriString());
            return ResponseEntity.created(location).body(roleService.save(category));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(roleService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}
