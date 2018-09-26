package com.airdrop;

import com.airdrop.entity.User;
import com.airdrop.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirdropApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
        String username = "mimocrys@166.com";
        User user = userRepository.findByUsername(username);
        System.err.println(user.toString());

    }

}
