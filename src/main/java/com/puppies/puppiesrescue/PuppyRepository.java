package com.puppies.puppiesrescue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PuppyRepository extends JpaRepository <Puppy, Long> {
    List<Puppy> findBySnContainsAndStatusContains(String sn, String status);
    Optional<Puppy> findById(Long id);
}
