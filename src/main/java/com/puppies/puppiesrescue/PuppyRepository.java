package com.puppies.puppiesrescue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuppyRepository extends JpaRepository <Puppy, Long> {
    List<Puppy> findByS_nContainsAndStatusContains(String s_n, String status);
}
