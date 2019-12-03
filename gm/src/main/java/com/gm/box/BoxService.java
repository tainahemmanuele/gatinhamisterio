package com.gm.box;

import com.gm.box.Box;
import com.gm.product.Product;
import com.gm.box.BoxRepository;
import com.gm.util.Validator;
import com.gm.util.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BoxService {

    @Autowired
    private BoxRepository boxRepository;

    private Validator validator = new Validator();
    public Flux<List<Box>> getAll(){
        List<Box> listBoxes = new ArrayList<Box>();
        Iterable<Box> boxesIterator = boxRepository.findAll();

        for (Box box : boxesIterator){
            listBoxes.add(box);
        }
        return Flux.just(listBoxes);
    }

    public Mono<Box> getById(Long id){
       return Mono.just(boxRepository.findById(id).get());
    }

    public Mono<Box> create(Box box) throws ValidatorException {
        return Mono.just(boxRepository.save(validCreate(box)));
    }

    public Mono<Box> update(Long id, Box boxUpdate)  throws ValidatorException{
        return Mono.just(boxRepository.save(validUpdate(boxRepository.findById(id).get(), boxUpdate)));
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
    private Box validCreate(Box box)  throws ValidatorException{
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
                throw new ValidatorException("Barcode already exists");
            }
        }else{
            return null;
        }
    }

    private Box validUpdate(Box box, Box boxUpdate)  throws ValidatorException {
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
