package com.gm.controller;

import com.gm.model.Box;
import com.gm.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BoxController {

    @Autowired
    private BoxService boxService;

    @GetMapping("/box")
    public List<Box> getAll() {
        System.out.println("GETTING ALL BOXES...");
        return boxService.getAll();
    }

    @GetMapping("/box/{id}")
    public ResponseEntity<Box> get(@PathVariable("id") Long id) {
        System.out.println("GET BOX BY ID "+ id + "...");
        Box box = boxService.getById(id);
        return (box != null)?
                new ResponseEntity<Box>(box, HttpStatus.OK):
                new ResponseEntity<Box>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/box")
    public Box create(@Valid @RequestBody Box box) {
        System.out.println("CREATE BOX " + box.getName() + "...");
        return boxService.create(box);
    }


    @PutMapping("/box/{id}")
    public ResponseEntity<Box> update(@PathVariable("id") Long id, @RequestBody Box box) {

        Box updatedUser = boxService.update(id, box);
        if(updatedUser != null) {
            return new ResponseEntity<Box>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/box/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        System.out.println("DELETE USER " + id + "...");

        if (boxService.delete(id)) {
            return new ResponseEntity<String>("User has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to delete.", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
