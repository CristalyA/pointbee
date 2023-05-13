package br.com.pointbee.afrotech.controller;

import br.com.pointbee.afrotech.model.Category;
import br.com.pointbee.afrotech.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Category>> getByName(@PathVariable String nome) {
        return ResponseEntity.ok(categoryRepository.findAllByNameContainingIgnoreCase(nome));
    }


    @PostMapping
    public ResponseEntity<Category> post(@RequestBody Category categories) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryRepository.save(categories));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> put(@PathVariable Long id, @RequestBody Category category) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isPresent()) {
            Category updatedCategory = categoryOpt.get();
            updatedCategory.setName(category.getName()); // assuming Category has a name field

            categoryRepository.save(updatedCategory);

            return ResponseEntity.ok(updatedCategory);
        }

        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Category> categories = categoryRepository.findById(id);
        if (categories.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        categoryRepository.deleteById(id);
    }

}
