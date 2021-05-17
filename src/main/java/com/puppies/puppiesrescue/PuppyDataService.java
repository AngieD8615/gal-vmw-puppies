package com.puppies.puppiesrescue;

import org.springframework.stereotype.Service;

@Service
public class PuppyDataService {

    PuppyRepository puppyRepository;
    public PuppyDataService (PuppyRepository puppyRepository) {
        this.puppyRepository = puppyRepository;
    }

    public PuppyList getPuppies() {
        return new PuppyList(puppyRepository.findAll());
    }

    public PuppyList getPuppies(String s_n, String status) {
        return null;
    }

    public Puppy addPuppy() {
        return null;
    }

    public Puppy getPuppyById(String id) {
        return null;
    }
}
