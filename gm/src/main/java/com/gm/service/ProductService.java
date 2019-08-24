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

    public List<Product> getAll(){
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
        Optional<Product> productData = productRepository.findById(id);
        if(productData.isPresent()){
            Product product = productData.get();
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


    public Product getById(Long id){
        Optional<Product> productData = productRepository.findById(id);
        if(productData.isPresent()){
            Product product = productData.get();
            return product;
        }else{
            return null; //posteriormente tratar isso com exceção
        }
    }

    public boolean delete(Long id){
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }




}
