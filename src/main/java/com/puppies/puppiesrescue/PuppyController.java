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
    public ResponseEntity<PuppyList> getAllPuppies(@RequestParam(required = false) String s_n,
                                                   @RequestParam(required = false) String status) {
        PuppyList puppyList;
        if(s_n == null && status == null) {
            puppyList = puppyDataService.getPuppies();
        } else {
            if (s_n == null) s_n = "";
            if (status == null) status = "";
            puppyList = puppyDataService.getPuppies(s_n, status);
        }

        return puppyList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(puppyList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Puppy> getPuppyById(@PathVariable String id) {
        Puppy puppy = puppyDataService.getPuppyById(id);
        if (puppy == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(puppy);
        }
    }


    @PostMapping
    public ResponseEntity<Puppy> addPuppy(@RequestBody Puppy addedPuppy) {
        Puppy newlyAddedPuppy;
        try {
            newlyAddedPuppy = puppyDataService.addPuppy(addedPuppy);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(newlyAddedPuppy);
    }
}
