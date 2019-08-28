package com.gm.service;

import com.gm.model.Box;
import com.gm.model.Product;
import com.gm.repository.BoxRepository;
import com.gm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BoxService {

    @Autowired
    private BoxRepository boxRepository;

    private Validator validator = new Validator();
    public List<Box> getAll(){
        List<Box> listBoxes = new ArrayList<Box>();
        Iterable<Box> boxesIterator = boxRepository.findAll();

        for (Box box : boxesIterator){
            listBoxes.add(box);
        }
        return listBoxes;
    }

    public Box getById(Long id){
        Optional<Box> boxData = boxRepository.findById(id);
        if(boxData.isPresent()){
            Box box  = boxData.get();
            return box;
        }else{
            return null;
        }
    }

    public Box create(Box box){
        Set<Product> products = box.getProducts();
        Box boxAux = validCreate(box);
        if(boxAux != null) {
            return boxRepository.save(boxAux);
        }else{
            return null;
        }
    }

    public Box update(Long id, Box boxUpdate){
        Optional<Box> boxData = boxRepository.findById(id);
        if(boxData.isPresent()){
            Box box = validUpdate(boxData.get(), boxUpdate);
            if(box != null) {
                return boxRepository.save(box);
            }else{
                return null;
            }
        } else{
            return null; //posteriormente tratar isso com exceção
        }
    }

    public boolean delete(Long id){
        Optional<Box> boxData = boxRepository.findById(id);
        if(boxData.isPresent()){
            boxRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    private boolean boxBarcodeExists(String barcode) {
        Iterable<Box> boxIterator = boxRepository.findAll();
        for (Box box : boxIterator) {
            if (box.getBarcode().equals(barcode)) {
                return true;
            }
        }
        return false;
    }
    private Box validCreate(Box box){
        if(validator.validString(box.getName()) && validator.validString(box.getBarcode()) && validator.validValue(box.getCost())
        && validator.validValue(box.getPrice()) && validator.validValueInt(box.getStock()) && validator.validListProduct(box.getProducts())){
            if (! boxBarcodeExists(box.getBarcode())){
                box.setName(box.getName());
                box.setProducts(box.getProducts());
                box.setBarcode(box.getBarcode());
                box.setCost(box.getCost());
                box.setPrice(box.getPrice());
                box.setStock(box.getStock());
                return box;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    private Box validUpdate(Box box, Box boxUpdate){
        if(validator.validString(boxUpdate.getName()) && validator.validString(boxUpdate.getBarcode()) && validator.validValue(boxUpdate.getCost())
                && validator.validValue(boxUpdate.getPrice()) && validator.validValueInt(boxUpdate.getStock()) && validator.validListProduct(boxUpdate.getProducts())){
                box.setName(boxUpdate.getName());
                box.setProducts(boxUpdate.getProducts());
                box.setBarcode(boxUpdate.getBarcode());
                box.setCost(boxUpdate.getCost());
                box.setPrice(boxUpdate.getPrice());
                box.setStock(boxUpdate.getStock());
                return box;

        }else{
            return null;
        }
    }
}
