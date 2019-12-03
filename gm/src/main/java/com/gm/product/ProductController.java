package com.gm.product;

import com.gm.util.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/product")
    public Flux<List<Product>> getAllProducts(){
        return productService.getAll();
    }

    @GetMapping("product/{id}")
    public Mono<Product> getProduct(@PathVariable("id") Long id){
        System.out.println("GETTING ALL PRODUCTS...");
        return productService.getById(id);

    }

    @PostMapping("/product")
    public Mono<Product> createProduct(@Valid @RequestBody Product product) throws ValidatorException {
        System.out.println("CREATE PRODUCT " + product.getName() + "...");
        return productService.create(product);

    }

    @PutMapping("/product/{id}")
    public Mono<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product)  throws ValidatorException{
        return productService.update(id, product);
    }

    @DeleteMapping("/product/{id}")
    public Mono<String> deleteProduct(@PathVariable("id") Long id) {
        System.out.println("DELETE PRODUCT " + id + "...");
        return Mono.just("Product has been deleted.");
    }

    @GetMapping("product/cache")
    public ResponseEntity<String> getCache(){
        String str = productService.debugCache();
        return new ResponseEntity<String>(str, HttpStatus.OK);
    }
}
