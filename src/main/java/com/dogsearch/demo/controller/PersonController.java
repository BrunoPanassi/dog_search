package com.dogsearch.demo.controller;

import com.dogsearch.demo.dto.category.CategorySaveDTO;
import com.dogsearch.demo.dto.person.PersonSaveDTO;
import com.dogsearch.demo.impl.PersonServiceImpl;
import com.dogsearch.demo.impl.RoleServiceImpl;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonServiceImpl personService;
    private final RoleServiceImpl roleService;

    @GetMapping()
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "0") Long id,
                              @RequestParam(name = "name", defaultValue = UtilParam.DEFAULT_STRING_PARAM_TO_REPO, required = false) String name) {
        try {
            return ResponseEntity.ok().body(personService.find(id, name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody PersonSaveDTO personBody) {
        try {
            Person person = new Person(personBody, UtilParam.DEFAULT_LONG_PARAM_TO_REPO);
            UtilParam.checkIfAllParamsAreFilled(personService.getParams(person), Person.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/person/save").toUriString());
            return ResponseEntity.created(location).body(personService.save(person));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PutMapping("/save/{id}")
    public ResponseEntity update(@RequestBody PersonSaveDTO personBody, @PathVariable("id") Long id) {
        try {
            Person person = new Person(personBody, id);
            UtilParam.checkIfAllParamsAreFilled(personService.getParams(person), Person.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/person/save/".concat(String.valueOf(id))).toUriString());
            return ResponseEntity.created(location).body(personService.save(person));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(personService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/role/add")
    public ResponseEntity addRole(@RequestParam("person_id") Long personId,
                                  @RequestParam("role_id") Long roleId) {
        try {
            roleService.addRoleAndSavePerson(personId, roleId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @GetMapping("/role")
    public ResponseEntity getRoles(@RequestParam("id") Long personId) {
        try {
            return ResponseEntity.ok().body(roleService.findRolesByPersonId(personId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @GetMapping("/announcements")
    public ResponseEntity getAnnouncements(@RequestParam("id") Long personId) {
        try {
            return ResponseEntity.ok().body(personService.findAnnouncementsByPersonId(personId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}
