package com.puppies.puppiesrescue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuppyRepository extends JpaRepository <Puppy, Long> {
}
