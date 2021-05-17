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
        return new PuppyList(puppyRepository.findBySnContainsAndStatusContains(s_n, status));
    }

    public Puppy addPuppy(Puppy puppy) {
        return puppyRepository.save(puppy);
    }

    public Puppy getPuppyById(Long id) {
        return puppyRepository.findById(id).orElse(null);
    }
}
