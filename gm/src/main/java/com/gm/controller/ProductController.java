package com.gm.controller;

import com.gm.model.Product;
import com.gm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        System.out.println("GETTING ALL PRODUCTS...");
        List<Product> products = productService.getAll();
        return products;
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        System.out.println("GETTING ALL PRODUCTS...");
        Product product = productService.getById(id);
        return (product != null)?
                new ResponseEntity<Product>(product, HttpStatus.OK):
                new ResponseEntity<Product>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/products/create")
    public Product createProduct(@Valid @RequestBody Product product){
        return productService.create(product);

    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        System.out.println("UPDATING PRODUCT " + id + "...");

        Product updatedProduct = productService.update(id, product);
        if(updatedProduct != null) {
            return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        System.out.println("DELETE PRODUCT " + id + "...");

        if (productService.delete(id)) {
            return new ResponseEntity<String>("Product has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to delete.Product does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }


}
