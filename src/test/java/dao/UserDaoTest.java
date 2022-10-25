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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
public class UserDaoTest {
    @Autowired
    ApplicationContext context;
    UserDao userDao;
    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao",UserDao.class);
    }

    @Test
    void addAndSelect() throws SQLException{

        String id = "333";
        User user = new User(id,"spring","1123");
        userDao.add(user);

        User selectedUser = userDao.findById(id);
        Assertions.assertEquals("spring",selectedUser.getName());
        Assertions.assertEquals("1123",selectedUser.getPassword());
        userDao.deleteAll();
    }
}
