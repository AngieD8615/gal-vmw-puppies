package com.puppies.puppiesrescue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
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

        when(puppyRepository.findBySnContainsAndStatusContains(anyString(), anyString()))
                .thenReturn(Arrays.asList(puppy));
        PuppyList puppyList = puppyDataService.getPuppies("true", "TOO_YOUNG");
        assertThat(puppyList).isNotNull();
        assertThat(puppyList.isEmpty()).isFalse();
    }

    @Test
    void addPuppy_valid_returnsPuppy() {
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
    void addPuppy_invalid_returnsNoContent() {
        when(puppyRepository.save(any(Puppy.class)))
                .thenReturn(null);
        Puppy puppy = puppyDataService.addPuppy(new Puppy());
        assertThat(puppy).isNull();
    }

    @Test
    void getPuppyById_valid_returnPuppy() {
        Puppy puppy = new Puppy("newPup", "white",
                Puppy.Status.READY, true,
                Puppy.Tail.CURLS, "dog");
        when(puppyRepository.findById(anyLong()))
                .thenReturn(Optional.of(puppy));

        Puppy puppyActual = puppyDataService.getPuppyById(99L);
        assertThat(puppyActual).isNotNull();
        assertThat(puppyActual.getName()).isEqualTo("newPup");

    }
}