package com.wiredbrain.friends.controller;


import com.wiredbrain.friends.model.Friend;
import com.wiredbrain.friends.service.FriendService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Unit tests
// @WebMvcTests - use Mockito MockMvc, @MockBean

@RunWith(SpringRunner.class)
@WebMvcTest(FriendController.class)
public class StandaloneControllerTests {

    @MockBean
    FriendService friendService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(get("/friend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testCreateReadDelete() throws Exception {
        Friend friend = new Friend("Gordon", "Moore");
        List<Friend> friends = Arrays.asList(friend);

        // mock returning a collection of Friends, containing a single Friend
        Mockito.when(friendService.findAll()).thenReturn(friends);

        mockMvc.perform(get("/friend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].first-name", Matchers.is("Gordon")));
    }

}
