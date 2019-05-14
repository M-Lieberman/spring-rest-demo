package com.wiredbrain.friends;

import com.wiredbrain.friends.model.Friend;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


// Testing the RestTemplate
// GET, POST, PUT, DELETE
public class SystemTests {

    @Test
    public void testCreateReadDeleteSystem() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/friend";

        // create test
        String firstName = "Clem";
        String lastName = "Fandango";

        // assumes an empty table
        Friend friend = new Friend(firstName, lastName);
        ResponseEntity<Friend> entity = restTemplate.postForEntity(url, friend, Friend.class);

        // read test
        Friend[] friends = restTemplate.getForObject(url, Friend[].class);
        Assertions.assertThat(friends).extracting(Friend::getFirstName).containsOnly(firstName);
//        Assertions.assertThat(friends).extracting(Friend::getLastName).containsOnly(firstName);

    // TODO add get to count current rows, delete, then recount
        restTemplate.delete(url + "/" + entity.getBody().getId());
        Assertions.assertThat(restTemplate.getForObject(url, Friend[].class)).isEmpty();
    }

    @Test
    public void testErrorHandlingReturnsBadRequest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/wrong";

        try {
            restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }
}
