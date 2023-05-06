package com.dogsearch.demo.repository;

import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {
    @Query(value = """
            SELECT
            subCategory
            FROM SubCategory subCategory
            WHERE UPPER(subCategory.name) LIKE CONCAT('%', UPPER(:name), '%') 
            """)
    SubCategory findByName(@Param("name") String name);

    @Query(value = """
            SELECT
            subCategory
            FROM SubCategory subCategory
            WHERE (:name = '_default_') OR (UPPER(subCategory.name) LIKE CONCAT('%', UPPER(:name), '%'))
            AND (:id = 0) OR (subCategory.id = :id) 
            """)
    List<SubCategory> findByIdAndName(@Param("id") Long id, @Param("name") String name);

    @Query(value = """
            SELECT
            subCategory
            FROM SubCategory subCategory
            JOIN subCategory.category
            WHERE (:categoryName = '_default_') OR (UPPER(category.name) LIKE CONCAT('%', UPPER(:categoryName), '%'))
            AND (:categoryId = 0) OR (category.id = :categoryId) 
            """)
    List<SubCategory> findByCategoryIdAndName(@Param("categoryId") Long categoryId, @Param("categoryName") String categoryName);
}
