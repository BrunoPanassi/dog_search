package com.dogsearch.demo.repository;

import com.dogsearch.demo.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
class CategoryRepoTest {

    @Autowired
    CategoryRepo categoryRepo;

    @AfterEach
    void tearDown() {
        categoryRepo.deleteAll();
    }

    @Test
    @Disabled
    void itShouldSaveCategory() {
        //given
        Category categoryToSave = new Category("Adestradores");

        //when
        categoryRepo.save(categoryToSave);

        //then
//        Category categoryFinded = categoryRepo.findByName("adestradores");
//        assertThat(categoryFinded).isEqualTo(categoryToSave);
    }

    @Test
    @Disabled
    void itShouldNotFindCategory() {
        //given
        Category categoryToSave = new Category("Caẽs de raça");

        //when
        categoryRepo.save(categoryToSave);

        //then
//        Category categoryFinded = categoryRepo.findByName("caes de raca");
//        assertThat(categoryFinded).isNull();
    }
}