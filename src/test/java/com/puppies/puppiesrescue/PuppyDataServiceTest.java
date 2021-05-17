package com.puppies.puppiesrescue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PuppyDataServiceTest {

    private PuppyDataService puppyDataService;

    @Mock
    PuppyRepository puppyRepository;

    @BeforeEach
    void setUp() {
        puppyDataService = new PuppyDataService(puppyRepository);
    }

    @Test
    void getPuppies() {
        PuppyList puppyList = puppyDataService.getPuppies();
        assertThat(puppyList).isNotNull();

    }

    @Test
    void testGetPuppies() {
    }

    @Test
    void addPuppy() {
    }

    @Test
    void getPuppyById() {
    }
}