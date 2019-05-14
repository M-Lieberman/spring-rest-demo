package com.wiredbrain.friends.controller;

import com.wiredbrain.friends.model.Friend;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;

// Testing SpringBootTest, Autowired


@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

    @Autowired
    FriendController friendController;


    @Test
    public void testCreateReadDeleteIntegration() {
        Friend friend = new Friend("Arya", "Stark");

        Friend friendResult = friendController.create(friend);
        // assumes an empty DB
        Iterable<Friend> friends = friendController.read();
        Assertions.assertThat(friends).first().hasFieldOrPropertyWithValue("firstName", "Arya");

        friendController.delete(friendResult.getId());
        Assertions.assertThat(friendController.read()).isEmpty();
    }

    @Test(expected= ValidationException.class)
    public void errorHandlingValidationExceptionThrown() {
        friendController.somethingIsWrong();
    }

}
