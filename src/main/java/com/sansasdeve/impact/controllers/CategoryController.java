package com.sansasdeve.impact.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sansasdeve.impact.domain.category.Category;
import com.sansasdeve.impact.service.CategoryService;

@RestController
@RequestMapping(value = "/categorys")
public class CategoryController {

  @Autowired
  CategoryService categoryService;

  @GetMapping()
  public ResponseEntity<Page<Category>> findAll(Pageable pageable) {
    Page<Category> response = categoryService.findAll(pageable);
    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Category> findById(@PathVariable Long id) {
    Category response = categoryService.findById(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<Category> register(@RequestBody Category data) {

    Category category = categoryService.register(data);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(category.getId())
        .toUri();

    return ResponseEntity.created(uri).body(category);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category data) {
    categoryService.update(id, data);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Category> delete(@PathVariable Long id) {
    categoryService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
