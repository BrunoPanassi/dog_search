package com.dogsearch.demo.repository;

import com.dogsearch.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query(value = """
            SELECT
            category
            FROM Category category
            WHERE UPPER(category.name) LIKE CONCAT('%', UPPER(:name), '%') 
            """)
    Category findByName(@Param("name") String name);
}
