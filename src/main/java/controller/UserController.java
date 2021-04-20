package controller;

import dao.UserDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.User;
import service.UserServiceDAO;
import utilities.DAOUtilities;

import java.util.List;

public class UserController {

    Javalin app;
    static UserDAO udao = DAOUtilities.getUserDAO();
    static UserServiceDAO usdao = DAOUtilities.getUserServiceDAO();

    public UserController(Javalin app) {
        this.app = app;
    }

    /**
     * <p>Endpoint handler that creates a session attribute "user" given a JSON
     * containing a username and password. A "user" attribute is required to access
     * any page containing sensitive data. I use body as class method to parse the data
     * for the sake of clarity and convenience.
     */
    public static void login(Context context){

        User login = context.bodyAsClass(User.class);
        String username = login.getUsername();
        String password = login.getPassword();

        if(usdao.login(username,password)){
            User user = udao.getUserByName(username);
            context.sessionAttribute("user", user);
            if(user.getUserRole().equals("Finance Manager")){
                context.result("admin");
            }else{
                context.result("user");
            }
        }else{
            context.result("false");
        }
    }

    /**
     * <p>An endpoint handler that clears the "user" session attribute by setting
     * it to null, thereby preventing the end user from accessing sensitive data.
     */
    public static void logout(Context context){
        context.sessionAttribute("user", null);
        context.redirect("/");
    }

    /**
     * Endpoint handler that registers a user account (Employee). To avoid a SQL Exception
     * (Duplicate Key), The method checks the username to see whether or not there is already
     * an account registered with the same username.
     */
    public static void register(Context context){
        User user = context.bodyAsClass(User.class);
        user.setUserRole("Employee");
        if(udao.verifyUsername(user.getUsername())){
            context.result("username taken");
        }else if(udao.addUser(user)){
            context.result("user added");
        }else {
            context.result("user not added");
        }
    }

    /**
     * <p>Endpoint handler that sends the user information (Excluding password) as
     * a JSON object back to the requester.
     */
    public static void getUser(Context context){
        User user = context.sessionAttribute("user");
        context.json(user);
    }

    /**
     * <p>An endpoint handler that sends an array containing the account details of all
     * registered users (excluding password), as a JSON object back to the requester.
     */
    public static void getAllUsers(Context context){
        List<User> users = udao.getAllUsers();
        context.json(users);
    }

    /**
     * <p>An endpoint handler to modify the user role attribute of a specified account.
     */
    public static void updateUserRole(Context context){
        User user = context.bodyAsClass(User.class);
        int userId = user.getUserId();
        String role = user.getUserRole();
        if(usdao.updateUserRole(userId, role)){
            context.result("true");
        }else{
            context.result("false");
        }
    }

}
