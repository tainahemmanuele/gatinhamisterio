package com.gm.service;

import com.gm.model.Product;
import com.gm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        List<Product> listProduct = new ArrayList<Product>();
        Iterable<Product> productsIterator = productRepository.findAll();

        for (Product product : productsIterator){
            listProduct.add(product);
        }
        return listProduct;
    }

    public Product create(Product product){
        return productRepository.save(product);
    }

    public Product update(Long id, Product productUpdate){
        Optional<Product> productSearch = productRepository.findById(id);
        if(productSearch.isPresent()){
            Product product = productSearch.get();
            product.setName(productUpdate.getName());
            product.setBarcode(productUpdate.getBarcode());
            product.setBrand(productUpdate.getBrand());
            product.setDistributor(productUpdate.getDistributor());
            product.setCost(productUpdate.getCost());
            product.setPrice(productUpdate.getPrice());
            product.setStock(productUpdate.getStock());

            return productRepository.save(product);
        }else{
            return null; //posteriormente tratar isso com exceção
        }
    }


    public Product getProductId(Long id){
        Optional<Product> productSearch = productRepository.findById(id);
        if(productSearch.isPresent()){
            Product product = productSearch.get();
            return product;
        }else{
            return null; //posteriormente tratar isso com exceção
        }
    }

    public boolean deleteProductId(Long id){
        Optional<Product> productSearch = productRepository.findById(id);
        if (productSearch.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }




}
