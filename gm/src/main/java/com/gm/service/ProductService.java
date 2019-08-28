package com.gm.service;

import com.gm.model.Product;
import com.gm.repository.ProductRepository;
import com.gm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private Validator validator = new Validator();

    public List<Product> getAll() {
        List<Product> listProduct = new ArrayList<Product>();
        Iterable<Product> productsIterator = productRepository.findAll();

        for (Product product : productsIterator) {
            listProduct.add(product);
        }
        return listProduct;
    }

    public Product create(Product product) {
        Product productAux = auxCreate(product);
        if(productAux != null){
            return productRepository.save(productAux);
        }
        return productAux;
    }

    public Product update(Long id, Product productUpdate) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            Product product = auxUpdate(productData.get(), productUpdate);
            System.out.println(product!=null);
            if(product != null){
                return productRepository.save(product);
            } else{
                return null;
            }

        } else {
            return null; //posteriormente tratar isso com exceção
        }
    }


    public Product getById(Long id) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            Product product = productData.get();
            return product;
        } else {
            return null; //posteriormente tratar isso com exceção
        }
    }

    public boolean delete(Long id) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    private boolean searchProductList(String barcode) {
        Iterable<Product> productsIterator = productRepository.findAll();
        for (Product product : productsIterator) {
            if (product.getBarcode().equals(barcode)) {
                return true;
            }
        }
        return false;
    }

    private Product auxCreate(Product product){
        if ((validator.validString(product.getName()) && validator.validString(product.getBarcode()) &&
                validator.validString(product.getBrand()) && validator.validString(product.getDistributor())
                && validator.validValue(product.getCost()) && validator.validValue(product.getPrice())
                && validator.validValueInt(product.getStock()) )) {
            if (!searchProductList(product.getBarcode())) {
                product.setName(product.getName());
                product.setBarcode(product.getBarcode());
                product.setBrand(product.getBrand());
                product.setDistributor(product.getDistributor());
                product.setCost(product.getCost());
                product.setPrice(product.getPrice());
                product.setStock(product.getStock());
                product.setType(product.getType());
                return product;
            }else {
                return null; //posteriormente tratar isso com exceção
            }
        }else {
            return null; //posteriormente tratar isso com exceção
        }
    }

    private Product auxUpdate(Product product, Product productUpdate){
        if ((validator.validString(productUpdate.getName()) && validator.validString(productUpdate.getBarcode()) &&
                validator.validString(productUpdate.getBrand()) && validator.validString(productUpdate.getDistributor())
                && validator.validValue(productUpdate.getCost()) && validator.validValue(productUpdate.getPrice())
                && validator.validValueInt(productUpdate.getStock()) )) {
                product.setName(productUpdate.getName());
                product.setBarcode(productUpdate.getBarcode());
                product.setBrand(productUpdate.getBrand());
                product.setDistributor(productUpdate.getDistributor());
                product.setCost(productUpdate.getCost());
                product.setPrice(productUpdate.getPrice());
                product.setStock(productUpdate.getStock());
                product.setType(productUpdate.getType());
                return product;

        }else {
            return null; //posteriormente tratar isso com exceção
        }
    }
}
