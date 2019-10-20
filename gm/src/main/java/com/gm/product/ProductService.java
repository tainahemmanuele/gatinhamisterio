package com.gm.product;

import com.gm.product.Product;
import com.gm.product.ProductRepository;
import com.gm.util.Validator;
import com.gm.util.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private CacheManager cache;

    public static final String CACHE_GETALL_KEY = "getAll";

    @Autowired
    private ProductRepository productRepository;
    private Validator validator = new Validator();

    @Cacheable(cacheNames=Product.CACHE_NAME,key="#root.target.CACHE_GETALL_KEY")
    public List<Product> getAll() {
        List<Product> listProduct = new ArrayList<Product>();
        Iterable<Product> productsIterator = productRepository.findAll();

        for (Product product : productsIterator) {
            listProduct.add(product);
        }
        simulateSlowService();
        return listProduct;
    }


    @CacheEvict(cacheNames = Product.CACHE_NAME,  key="#root.target.CACHE_GETALL_KEY")
    public Product create(Product product)  throws ValidatorException{
        Product productAux = validCreate(product);
        if(productAux != null){
            return productRepository.save(productAux);
        }
        return productAux;
    }

    @Caching(   evict = {@CacheEvict(cacheNames = Product.CACHE_NAME, key="#root.target.CACHE_GETALL_KEY")},
                put = {@CachePut(cacheNames = Product.CACHE_NAME, key="#id")})
    public Product update(Long id, Product productUpdate)  throws ValidatorException{
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            Product product = validUpdate(productData.get(), productUpdate);
            if(product != null){
                return productRepository.save(product);
            } else{
                return null;
            }

        } else {
            return null; //posteriormente tratar isso com exceção
        }
    }

    @Cacheable(cacheNames=Product.CACHE_NAME,key="#id")
    public Product getById(Long id) {
        Optional<Product> productData = productRepository.findById(id);
        simulateSlowService();
        if (productData.isPresent()) {
            Product product = productData.get();
            return product;
        } else {
            return null; //posteriormente tratar isso com exceção
        }
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = Product.CACHE_NAME, key="#id"),
            @CacheEvict(cacheNames = Product.CACHE_NAME, key="#root.target.CACHE_GETALL_KEY")
    })
    public boolean delete(Long id) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    private boolean productBarcodeExists(String barcode) {
        Iterable<Product> productsIterator = productRepository.findAll();
        for (Product product : productsIterator) {
            if (product.getBarcode().equals(barcode)) {
                return true;
            }
        }
        return false;
    }

    private Product validCreate(Product product) throws ValidatorException {
        if ((validator.validString(product.getName()) && validator.validString(product.getBarcode()) &&
                validator.validString(product.getBrand()) && validator.validString(product.getDistributor())
                && validator.validValue(product.getCost()) && validator.validValue(product.getPrice())
                && validator.validValueInt(product.getStock()) && validator.validProductType(product.getType()) )) {
            if (!productBarcodeExists(product.getBarcode())) {
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
                throw new ValidatorException("Barcode already exists");
            }
        }else {
            return null; //posteriormente tratar isso com exceção
        }
    }

    private Product validUpdate(Product product, Product productUpdate)  throws ValidatorException{
        if ((validator.validString(productUpdate.getName()) && validator.validString(productUpdate.getBarcode()) &&
                validator.validString(productUpdate.getBrand()) && validator.validString(productUpdate.getDistributor())
                && validator.validValue(productUpdate.getCost()) && validator.validValue(productUpdate.getPrice())
                && validator.validValueInt(productUpdate.getStock()) && validator.validProductType(productUpdate.getType()))) {
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

    private void simulateSlowService () {
        try {
            Thread.sleep (500L);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }

    public String debugCache(){
        String str = "Caches:";
        for(String cacheName : cache.getCacheNames()){
            str += "\nCache Name: " + cacheName + ": " + cache.getCache(cacheName).toString()+"";
            Object obj = cache.getCache(cacheName).getNativeCache();
        }
        return str;
    }

}
