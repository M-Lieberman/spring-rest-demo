package com.wiredbrain.friends.service;

import com.wiredbrain.friends.model.Friend;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@RunWith(SpringRunner.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceTests {

    @Autowired
    FriendService friendService;

    @Test
    public void testCreateReadDelete() {

        long count = friendService.count();


        Friend friend = new Friend("Eden", "Hazard");

        friendService.save(friend);

        Iterable<Friend> friends = friendService.findAll();
        if (count == 0) {
            Assertions.assertThat(friends).extracting(Friend::getFirstName).containsOnly("Eden");
        } else {
            Assertions.assertThat(friends).extracting(Friend::getFirstName).contains("Eden");
        }
        friendService.deleteAll();
        Assertions.assertThat(friendService.findAll()).isEmpty();
    }

}