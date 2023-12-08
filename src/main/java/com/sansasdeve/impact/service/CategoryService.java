package com.sansasdeve.impact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sansasdeve.impact.domain.category.Category;
import com.sansasdeve.impact.repositories.CategoryRepository;
import com.sansasdeve.impact.service.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  @Transactional
  public Category findById(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Recurso não encotrado"));
  }

  @Transactional
  public Page<Category> findAll(Pageable pageable) {
    return categoryRepository.findAll(pageable);
  }

  @Transactional
  public Category register(Category entity) {
    return categoryRepository.save(entity);

  }

  @Transactional
  public Category update(Long id, Category entity) {
    categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Id nao encontrado"));
    entity.setId(id);
    return categoryRepository.save(entity);
  }

  @Transactional
  public void delete(Long id) {
    categoryRepository.deleteById(id);
  }

}
