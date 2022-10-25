package dao;


import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
public class UserDaoTest {
    @Autowired
    ApplicationContext context;
    UserDao userDao;
    User user1, user2, user3;
    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao",UserDao.class);
        user1 = new User("1","김기헌","1111");
        user2 = new User("2","기헌","2222");
        user3 = new User("3","Kiheon","3333");
    }

    @Test
    void addAndSelect() throws SQLException{
        userDao.deleteAll();
        Assertions.assertEquals(0,userDao.getCount());
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        Assertions.assertEquals(3,userDao.getCount());
        userDao.deleteAll();
    }
}