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
    public ResponseEntity<Product> post(@RequestBody Product product) {
        if (categoryRepository.existsById(product.getCategories().getId())) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productRepository.save(product));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> put(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product updatedProduct = productOpt.get();
            updatedProduct.setNameProduct(product.getNameProduct());
            updatedProduct.setQuantity(product.getQuantity());
            updatedProduct.setQuality(product.getQuality());
            updatedProduct.setStatus(product.getStatus());
            updatedProduct.setCategories(product.getCategories());

            if (categoryRepository.existsById(product.getCategories().getId())) {
                return ResponseEntity.ok(productRepository.save(updatedProduct));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
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
