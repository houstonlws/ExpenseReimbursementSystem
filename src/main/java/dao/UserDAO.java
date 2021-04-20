package dao;

import model.User;

import java.util.List;

public interface UserDAO {

    /**
     * <p>Adds a user account to the database
     * @param user - The user object to be added to the database
     * @return - A ture or false value depending on whther or not the account was successfully added
     */
    boolean addUser(User user);

    /**
     * <p> Deletes a user account from the database
     * @param user - The user object containing the details of the account to be deleted
     * @return - A true or false value depending on whether or not the account was deleted or not
     */
    boolean deleteUser(User user);

    /**
     * <p> Updates a user account in the database given a user object
     * @param user - The user object containing the details of the account to be updated
     * @return - A true or false value based on whether or not the account was updated
     */
    boolean updateUser(User user);

    /**
     * <p>Retrieves a user object given a user id
     * @param id - The id of the account to be retrieved from the database
     * @return - A user object that contains the account details of the associated id
     */
    User getUserById(int id);

    /**
     * <p> Retrieves a user object from the database given a username
     * @param username - The username of the account to pull from the database
     * @return - A user object containing the details of the account with the associated username
     */
    User getUserByName(String username);

    /**
     * <p> Retrieves a list of all us accounts in the database
     * @return - A list of user objects containing the account details of all the users in the database
     */
    List<User> getAllUsers();

    /**
     * <p> Checks to see whether or not a username exists in the database
     * @param username - The username to compare against other usernames in the database
     * @return - A true or false value based on whether or not the username exists in the database
     */
    boolean verifyUsername(String username);

    /**
     * <p> Checks to see whether a password matches a username in the database
     * @param username - The username of the account to verify the password of
     * @param password - The password being compared with the username
     * @return A true or false value based on whether or not the password corresponds with the username
     */
    boolean verifyPassword(String username, String password);

    /**
     * <p> Gets the string value of an account role given an id
     * @param id - The id of the account role to get the string value of
     * @return The string value of an account role
     */
    String getRoleById(int id);

    /**
     * <p>Returns the id value of an account role given its string value
     * @param role - The string value of the user role to get the string value of
     * @return An integer that represents the id of a user role
     */
    int getIdByRole(String role);
}
