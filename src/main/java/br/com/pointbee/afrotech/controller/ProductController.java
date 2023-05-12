package br.com.pointbee.afrotech.controller;


import br.com.pointbee.afrotech.model.Product;
import br.com.pointbee.afrotech.repository.CategoryRepository;
import br.com.pointbee.afrotech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return productRepository.findById(id).map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Product>> getByStatus(@PathVariable String status){
        return ResponseEntity.ok(productRepository.findAllByStatusContainingIgnoreCase(status));
    }

    @PostMapping
    public ResponseEntity<Product> post(@Valid @RequestBody Product products){
        if (categoryRepository.existsById(products.getCategories().getId()))
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productRepository.save(products));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Product> put(@Valid @RequestBody Product products){
        if (productRepository.existsById(products.getId())){

            if (categoryRepository.existsById(products.getCategories().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(productRepository.save(products));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Product> products = productRepository.findById(id);

        if (products.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        productRepository.deleteById(id);
    }

}
