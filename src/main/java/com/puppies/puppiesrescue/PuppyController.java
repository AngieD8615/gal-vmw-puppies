package com.puppies.puppiesrescue;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puppies")
public class PuppyController {
    PuppyDataService puppyDataService;

    public PuppyController(PuppyDataService puppyDataService){
        this.puppyDataService = puppyDataService;
    }

    @GetMapping
    public ResponseEntity<PuppyList> getAllPuppies() {
        PuppyList puppyList = puppyDataService.getPuppies();
        return puppyList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(puppyList);

    }

    @PostMapping
    public ResponseEntity<Puppy> addPuppy(@RequestBody Puppy addedPuppy) {
        Puppy newlyAddedPuppy;
        try {
            newlyAddedPuppy = puppyDataService.addPuppy();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(newlyAddedPuppy);
    }
}
