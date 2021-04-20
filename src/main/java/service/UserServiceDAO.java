package service;

import model.User;

public interface UserServiceDAO {

    /**
     * <p>Verifies username exists and that username matches password
     * @param username - username of account holder
     * @param password - password of account holder
     * @return A true or false value depending on whether or not the information is correct
     */
    boolean login(String username, String password);

    /**
     * <p>Checks to see if a user role is Finance Manager
     * @param user - The user for which to check the user role of
     * @return A true or false value depending on whether or not the user is a finance manager
     */
    boolean isAdmin(User user);

    /**
     * <p> Changes the user role for a particular id
     * @param userId - The user id for which to change the user role of
     * @param role - The role to change to user to
     * @return A true or false false depending on whether or not the update was successful
     */
    boolean updateUserRole(int userId, String role);
}
