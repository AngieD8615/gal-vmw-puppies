package com.puppies.puppiesrescue;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class puppyControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PuppyDataService puppyDataService;

    ObjectMapper mapper = new ObjectMapper();
    List<Puppy> puppies;

    @BeforeEach
    void setUp() {
        Puppy.Tail[] tailOpt = new Puppy.Tail[] {Puppy.Tail.CURLS, Puppy.Tail.NONE, Puppy.Tail.STRAIGHT};
        Puppy.Status[] statusOpt = new Puppy.Status[] {Puppy.Status.ADOPTED, Puppy.Status.READY, Puppy.Status.TOO_YOUNG, Puppy.Status.SICK};
        String[] colors = new String[] {"white", "light brown", "brown", "black", "multi"};

        puppies = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 25; i++){
            puppies.add(new Puppy("name" + i, colors[r.nextInt(100) % 5], statusOpt[r.nextInt(100) % 4], true, tailOpt[r.nextInt(100) % 3], "breed" + i));
        }
    }

    @Test
    void getAll_noParams_exists_returnObjectWithPuppiesList () throws Exception {
        when(puppyDataService.getPuppies()).thenReturn(new PuppyList(puppies));

        mockMvc.perform(get("/api/puppies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puppies", hasSize(25)));
    }

    @Test
    void getAll_noParams_doesNotExists_returnNoContext () throws Exception {
        List<Puppy> empty = new ArrayList<>();
        when(puppyDataService.getPuppies()).thenReturn(new PuppyList());

        mockMvc.perform(get("/api/puppies"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addPuppy_Success_returnPuppy () throws Exception {

        Puppy addedPuppy = new Puppy("newPup", "white",
                                        Puppy.Status.READY, true,
                                        Puppy.Tail.CURLS, "dog");
        String json = mapper.writeValueAsString(addedPuppy);
        when(puppyDataService.addPuppy()).thenReturn(addedPuppy);

        mockMvc.perform(post("/api/puppies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("newPup"))
                .andExpect(jsonPath("status").value("READY"));
    }

    @Test
    void addPuppy_invalid_returnBadRequest () throws Exception {

        when(puppyDataService.addPuppy()).thenThrow(new InvalidPuppyException());

        mockMvc.perform(post("/api/puppies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"bad\"}"))
                .andExpect(status().isBadRequest());
    }
}
