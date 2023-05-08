package com.dogsearch.demo.repository;

import com.dogsearch.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query(value = """
            SELECT
            category
            FROM Category category
            WHERE UPPER(category.name) LIKE CONCAT('%', UPPER(:name), '%') 
            """)
    List<Category> findByName(@Param("name") String name);

    @Query(value = """
            SELECT
            category
            FROM Category category
            WHERE (:name = '_default_') OR (UPPER(category.name) LIKE CONCAT('%', UPPER(:name), '%'))
            AND (:id = 0) OR (category.id = :id) 
            """)
    List<Category> findByIdAndName(@Param("id") Long id, @Param("name") String name);
}
