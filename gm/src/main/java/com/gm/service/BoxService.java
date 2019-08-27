package com.gm.service;

import com.gm.model.Box;
import com.gm.model.Product;
import com.gm.repository.BoxRepository;
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
        return boxRepository.save(box);
    }

    public Box update(Long id, Box boxUpdate){
        Optional<Box> boxData = boxRepository.findById(id);
        if(boxData.isPresent()){
            Box box = boxData.get();
            box.setName(boxUpdate.getName());
            box.setProducts(boxUpdate.getProducts());
            box.setBarcode(boxUpdate.getBarcode());
            box.setCost(box.getCost());
            box.setPrice(box.getPrice());
            box.setStock(box.getStock());

            return boxRepository.save(box);
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

}
