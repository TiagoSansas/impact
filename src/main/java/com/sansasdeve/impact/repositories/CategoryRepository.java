package com.sansasdeve.impact.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sansasdeve.impact.domain.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
