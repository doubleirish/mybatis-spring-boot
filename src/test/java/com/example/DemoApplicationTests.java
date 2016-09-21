package com.example;

import com.example.dao.UserDao;
import com.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

  @Autowired
  UserDao userDao;

  	@Test
  	public void findsSomeUsers() {

      List<User> allUsers = userDao.findOrderedUsers();
      allUsers.forEach(user -> System.out.println(user));
      assertThat(allUsers.size(),is(greaterThan(0)));
    }

}
