package br.com.pointbee.afrotech.controller;

import br.com.pointbee.afrotech.model.Category;
import br.com.pointbee.afrotech.repository.CategoryRepository;
import jakarta.validation.Valid;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id){
        return categoryRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Category>> getByName(@PathVariable String name){
        return ResponseEntity.ok(categoryRepository.findAllByNameContainingIgnoreCase(name));
    }


    @PostMapping
    public ResponseEntity<Category> post (@Valid @RequestBody Category categories){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryRepository.save(categories));
    }

    @PutMapping
    public ResponseEntity<Category> put(@Valid @RequestBody Category categories) {
        return categoryRepository.findById(categories.getId())
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(categoryRepository.save(categories)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Category> categories = categoryRepository.findById(id);
        if(categories.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        categoryRepository.deleteById(id);
    }

}
