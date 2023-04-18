package com.dogsearch.demo.impl;

import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.repository.CategoryRepo;
import com.dogsearch.demo.util.exception.UtilException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepo categoryRepo;

    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepo);
    }

    @Test
    void itShouldSaveCategory() throws Exception {
        //given
        Category categoryToSave = new Category("Cães de raça");

        //when
        categoryService.save(categoryToSave);

        //then
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepo).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();

        assertThat(capturedCategory).isEqualTo(categoryToSave);
    }

    @Test
    void itShouldNotSaveCategoryBecauseAlreadyExists() throws Exception {
        //given
        List<String> exceptionMessageParams = new ArrayList<>() {{add(Category.objectNamePtBr);}};
        Category categoryToSave = new Category("Cães de raça");

        //when
        given(categoryRepo.findByName(categoryToSave.getName())).willReturn(categoryToSave);

        //then
        assertThatThrownBy(() -> categoryService.save(categoryToSave))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.exceptionMessageBuilder(UtilException.ALREADY_EXISTS_WITH_PARAM, exceptionMessageParams));

        verify(categoryRepo, never()).save(any());
    }

    @Test
    void itShouldDeleteCategory() throws Exception {
        //given
        Category categoryToSave = new Category("Cães de raça");
        categoryToSave.setId(11L);

        given(categoryService.find(categoryToSave.getName())).willReturn(categoryToSave);

        //when
        categoryService.delete(categoryToSave);

        //then
        verify(categoryRepo).deleteById(categoryToSave.getId());
    }
}