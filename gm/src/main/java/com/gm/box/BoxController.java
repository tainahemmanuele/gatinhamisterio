package com.gm.box;

import com.gm.util.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
//import javax.xml.ws.Response;
import java.util.List;

@RestController
public class BoxController {

    @Autowired
    private BoxService boxService;

    @GetMapping("/box")
    public ResponseEntity<List<Box>> getAllBoxes() {
        System.out.println("GETTING ALL BOXES...");
        List<Box> boxes = boxService.getAll();
        if (boxes.isEmpty())
            return new ResponseEntity<List<Box>>(boxes,HttpStatus.OK);
        return new ResponseEntity<List<Box>>(boxes,HttpStatus.OK);
    }

    @GetMapping("/box/{id}")
    public ResponseEntity<Box> getBox(@PathVariable("id") Long id) {
        System.out.println("GET BOX BY ID "+ id + "...");
        Box box = boxService.getById(id);
        return (box != null)?
                new ResponseEntity<Box>(box, HttpStatus.OK):
                new ResponseEntity<Box>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/box")
    public ResponseEntity<Box> createBox(@Valid @RequestBody Box box)  throws ValidatorException {
        System.out.println("CREATE BOX " + box.getName() + "...");
        Box newBox = boxService.create(box);
        if (newBox == null)
            return new ResponseEntity<Box>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Box>(newBox,HttpStatus.OK);
    }


    @PutMapping("/box/{id}")
    public ResponseEntity<Box> update(@PathVariable("id") Long id, @RequestBody Box box)  throws ValidatorException{
        Box updatedBox = boxService.update(id, box);
        if(updatedBox != null) {
            return new ResponseEntity<Box>(updatedBox, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/box/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        System.out.println("DELETE BOX " + id + "...");

        if (boxService.delete(id)) {
            return new ResponseEntity<String>("Box has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to delete. Box does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
