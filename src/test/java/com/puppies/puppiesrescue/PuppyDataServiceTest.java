package com.puppies.puppiesrescue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
    void getPuppies_noArgs_returnsList() {
        Puppy puppy = new Puppy("newPup", "white",
                Puppy.Status.READY, true,
                Puppy.Tail.CURLS, "dog");

        when(puppyRepository.findAll()).thenReturn(Arrays.asList(puppy));
        PuppyList puppyList = puppyDataService.getPuppies();
        assertThat(puppyList).isNotNull();
        assertThat(puppyList.isEmpty()).isFalse();

    }

    @Test
    void testGetPuppies_withParams_returnsList() {
        Puppy puppy = new Puppy("newPup", "white",
                Puppy.Status.READY, true,
                Puppy.Tail.CURLS, "dog");

        when(puppyRepository.findByS_nContainsAndStatusContains(anyString(), anyString()))
                .thenReturn(Arrays.asList(puppy));
        PuppyList puppyList = puppyDataService.getPuppies("true", "TOO_YOUNG");
        assertThat(puppyList).isNotNull();
        assertThat(puppyList.isEmpty()).isFalse();
    }

    @Test
    void addPuppy() {
        Puppy puppy = new Puppy("newPup", "white",
                Puppy.Status.READY, true,
                Puppy.Tail.CURLS, "dog");

        when(puppyRepository.save(any(Puppy.class)))
                .thenReturn(puppy);
        Puppy newlyAddedPuppy = puppyDataService.addPuppy(puppy);
        assertThat(puppy).isNotNull();
        assertThat(puppy.getName()).isEqualTo("newPup");
    }

    @Test
    void getPuppyById() {
    }
}