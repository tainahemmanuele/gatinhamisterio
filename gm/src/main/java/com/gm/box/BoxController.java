package com.gm.box;

import com.gm.util.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
//import javax.xml.ws.Response;
import java.util.List;

@RestController
public class BoxController {

    @Autowired
    private BoxService boxService;

    @GetMapping("/box")
    public Flux<List<Box>> getAllBoxes() {
        System.out.println("GETTING ALL BOXES...");
        return boxService.getAll();
    }

    @GetMapping("/box/{id}")
    public Mono<Box> getBox(@PathVariable("id") Long id) {
        return boxService.getById(id);
    }

    @PostMapping("/box")
    public Mono<Box> createBox(@Valid @RequestBody Box box)  throws ValidatorException {
        System.out.println("CREATE BOX " + box.getName() + "...");
        return boxService.create(box);
    }


    @PutMapping("/box/{id}")
    public Mono<Box> update(@PathVariable("id") Long id, @RequestBody Box box)  throws ValidatorException{
        return boxService.update(id, box);
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
