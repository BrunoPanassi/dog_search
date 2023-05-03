package com.dogsearch.demo.repository;

import com.dogsearch.demo.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {
}
