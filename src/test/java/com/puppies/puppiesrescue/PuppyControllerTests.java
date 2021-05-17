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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PuppyControllerTests {
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
            Puppy curPuppy = new Puppy("name" + i, colors[r.nextInt(100) % 5],
                    statusOpt[r.nextInt(100) % 4], true,
                    tailOpt[r.nextInt(100) % 3], "breed" + i);
            curPuppy.setId(i);

            puppies.add(curPuppy);
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
    void getById_exists_returnPuppy () throws Exception {
        Puppy puppyExists = new Puppy("newPup", "white",
                Puppy.Status.READY, true,
                Puppy.Tail.CURLS, "dog");
        puppyExists.setId(100);

        when(puppyDataService.getPuppyById(anyLong())).thenReturn(puppyExists);

        mockMvc.perform(get("/api/puppies/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("newPup"))
                .andExpect(jsonPath("id").value("100"))
                .andExpect(jsonPath("color").value("white"));
    }

    @Test
    void getById_doesNotExists_returnNoContent () throws Exception {
        when(puppyDataService.getPuppyById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/puppies/100"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAll_withParams_snAndStatus_exists_returnPuppyList () throws Exception {
        when(puppyDataService.getPuppies(anyString(), anyString())).thenReturn(new PuppyList(puppies));

        mockMvc.perform(get("/api/puppies?s_n=true&status=SICK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puppies", hasSize(25)));
    }

    @Test
    void addPuppy_Success_returnPuppy () throws Exception {

        Puppy addedPuppy = new Puppy("newPup", "white",
                                        Puppy.Status.READY, true,
                                        Puppy.Tail.CURLS, "dog");
        addedPuppy.setId(100);
        String json = mapper.writeValueAsString(addedPuppy);
        when(puppyDataService.addPuppy(any(Puppy.class))).thenReturn(addedPuppy);

        mockMvc.perform(post("/api/puppies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("newPup"))
                .andExpect(jsonPath("id").value("100"))
                .andExpect(jsonPath("status").value("READY"));
    }

    @Test
    void addPuppy_invalid_returnBadRequest () throws Exception {

        when(puppyDataService.addPuppy(any(Puppy.class))).thenThrow(new InvalidPuppyException());

        mockMvc.perform(post("/api/puppies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"bad\"}"))
                .andExpect(status().isBadRequest());
    }

}
