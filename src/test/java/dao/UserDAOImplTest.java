package dao;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.DAOUtilities;
import utilities.Testing;

import java.util.List;

class UserDAOImplTest {
    UserDAO dao = DAOUtilities.getUserDAO();
    int userId = 7;
    String username = "TestUser";
    String password = "password";
    String firstName = "Test";
    String lastName = "User";
    String email = "test@user.com";
    String role = "Employee";
    User user = new User(username,password,firstName,lastName,email,role);
    User user2 = new User(userId, username,password,firstName,lastName,email,role);

    @BeforeEach
    void setUp() {
        Testing.h2Init();
    }

    @AfterEach
    void tearDown() {
        Testing.h2Destroy();
    }

    @Test
    void addUserTest(){
        boolean b = dao.addUser(user);
        System.out.println(b);
    }

    @Test
    void deleteUserTest(){
        boolean b = dao.deleteUser(user);
        System.out.println(b);
    }

    @Test
    void updateUserTest(){
        boolean a = dao.addUser(user);
        boolean b = dao.updateUser(user2);
        System.out.println(a +" "+ b);
    }

    @Test
    void getUserByIdTest(){
        User u = dao.getUserById(6);
        System.out.println(u);
    }

    @Test
    void getAllUsersTest(){
        List<User> users = dao.getAllUsers();
        for(User u : users){
            System.out.println(u);
        }
    }

    @Test
    void verifyUsernameTest(){
        boolean b = dao.verifyUsername("clarkK77");
        System.out.println(b);
    }

    @Test
    void verifyPasswordTest(){
        boolean b = dao.verifyPassword("clarkK77","$2a$06$PWJnpEhOiKJ4PgmP1.QHM.77QxuVydKDJnZQAqh2X49e0Ll4vHc2O");
        System.out.println(b);
    }

}