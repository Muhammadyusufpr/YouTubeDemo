package com.company.youtube.repository;

import com.company.youtube.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

}
