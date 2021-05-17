package com.puppies.puppiesrescue;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebMvcTest
public class puppyControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PuppyDataService puppyDataService;

    List<Puppy> puppies;

    @BeforeEach
    void setUp() {
        Puppy.Tail[] tailOpt = new Puppy.Tail[] {Puppy.Tail.CURLS, Puppy.Tail.NONE, Puppy.Tail.STRAIGHT};
        Puppy.Status[] statusOpt = new Puppy.Status[] {Puppy.Status.ADOPTED, Puppy.Status.READY, Puppy.Status.TOO_YOUNG, Puppy.Status.SICK};
        String[] colors = new String[] {"white", "light brown", "brown", "black", "multi"};

        puppies = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 25; i++){
            puppies.add(new Puppy("name" + i, colors[r.nextInt() % 5], statusOpt[r.nextInt() % 4], true, tailOpt[r.nextInt() % 3], "breed" + i));
        }
    }

}
