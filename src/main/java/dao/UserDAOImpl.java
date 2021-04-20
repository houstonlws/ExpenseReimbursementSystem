package dao;

import model.User;
import utilities.DAOUtilities;
import utilities.Logging;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    Logging logger = Logging.getInstance();
    String myclass = UserDAOImpl.class.getName();
    Connection connection = null;
    PreparedStatement stmt = null;
    PreparedStatement stmt2 = null;//Used for when i have a nested query

    //---------------------------------------------------------------------------------------------//

    @Override
    public boolean addUser(User user) {
        try {
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO ers_users VALUES (DEFAULT,?,?,?,?,?,?);";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getEmail());
            stmt.setInt(6, getIdByRole(user.getUserRole()));

            if(stmt.executeUpdate() != 0){
                logger.info(myclass,"Added user " + user.getUsername() + " to database.");
                DAOUtilities.closeConnection();
                return true;
            } else {
                logger.info(myclass,"User " + user.getUsername() + " was not added to database.");
                DAOUtilities.closeConnection();
                return false;
            }


        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass, "Error adding user to database.", e);
            return false;
        }
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public boolean deleteUser(User user) {
        try {
            connection = DAOUtilities.getConnection();
            String sql = "DELETE FROM ers_users WHERE ers_username=?;";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, user.getUsername());

            if(stmt.executeUpdate() != 0){
                logger.info(myclass,"User " + user.getUsername() + " removed from database.");
                return true;
            } else {
                logger.info(myclass,"User " + user.getUsername() + " was not removed from database..");
                return false;
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass,"Error removing user from database.", e);
            return false;
        }
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public boolean updateUser(User user) {
        try {
            connection = DAOUtilities.getConnection();
            String sql = "UPDATE ers_users SET " +
                    "ers_username=?," +
                    "ers_password=?," +
                    "user_first_name=?," +
                    "user_last_name=?," +
                    "user_email=?," +
                    "ers_user_role_id=? WHERE ers_user_id=?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getEmail());
            stmt.setInt(6, getIdByRole(user.getUserRole()));
            stmt.setInt(7, user.getUserId());

            if(stmt.executeUpdate() != 0){
                logger.info(myclass,"Updated user " + user.getUsername() + ".");
                return true;
            } else {
                logger.info(myclass,"User " + user.getUsername() + " was not updated.");
                return false;
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass, "Error updating user.", e);
            return false;
        }    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public User getUserById(int id) {
        User user = null;
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM users WHERE ers_user_id=?;";
            stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rsUserId= rs.getInt("ers_user_id");
                String rsUsername = rs.getString("ers_username");
                String rsPassword = rs.getString("ers_password");
                String rsFirstName = rs.getString("user_first_name");
                String rsLastName = rs.getString("user_last_name");
                String rsEmail = rs.getString("user_email");
                String rsUserRole = rs.getString("user_role");
                user = new User();
                user.setUserId(rsUserId);
                user.setUsername(rsUsername);
                user.setPassword(rsPassword);
                user.setFirstName(rsFirstName);
                user.setLastName(rsLastName);
                user.setEmail(rsEmail);
                user.setUserRole(rsUserRole);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return user;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public User getUserByName(String username) {
        User user = null;
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM users WHERE ers_username=?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rsUserId= rs.getInt("ers_user_id");
                String rsUsername = rs.getString("ers_username");
                String rsPassword = rs.getString("ers_password");
                String rsFirstName = rs.getString("user_first_name");
                String rsLastName = rs.getString("user_last_name");
                String rsEmail = rs.getString("user_email");
                String rsUserRole = rs.getString("user_role");
                user = new User();
                user.setUserId(rsUserId);
                user.setUsername(rsUsername);
                user.setPassword(rsPassword);
                user.setFirstName(rsFirstName);
                user.setLastName(rsLastName);
                user.setEmail(rsEmail);
                user.setUserRole(rsUserRole);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return user;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM ers_users;";
            stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rsUserId= rs.getInt("ers_user_id");
                String rsUsername = rs.getString("ers_username");
                String rsFirstName = rs.getString("user_first_name");
                String rsLastName = rs.getString("user_last_name");
                String rsEmail = rs.getString("user_email");
                String rsUserRoleId = getRoleById(rs.getInt("ers_user_role_id"));
                User u = new User();
                u.setUserId(rsUserId);
                u.setUsername(rsUsername);
                u.setFirstName(rsFirstName);
                u.setLastName(rsLastName);
                u.setEmail(rsEmail);
                u.setUserRole(rsUserRoleId);
                list.add(u);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return list;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public boolean verifyUsername(String username) {

        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM ers_users;";
            stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String rsUsername = rs.getString("ers_username");
                if (rsUsername.equals(username)) {
                    return true;
                }
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public boolean verifyPassword(String username, String password) {
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM ers_users;";
            stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String rsUsername = rs.getString("ers_username");
                String rsPassword = rs.getString("ers_password");
                if (rsUsername.equals(username) && rsPassword.equals(password)) {
                    return true;
                }
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public String getRoleById(int id) {
        String role = "";
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM ers_user_roles WHERE ers_user_role_id=?;";
            stmt2 = connection.prepareStatement(sql);

            stmt2.setInt(1, id);
            ResultSet rs = stmt2.executeQuery();

            while (rs.next()) {
                String rsName = rs.getString("user_role");
                role = rsName;
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return role;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public int getIdByRole(String role) {
        int id = 0;
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM ers_user_roles WHERE user_role=?;";
            stmt2 = connection.prepareStatement(sql);

            stmt2.setString(1, role);
            ResultSet rs = stmt2.executeQuery();

            while (rs.next()) {
                int rsId= rs.getInt("ers_user_role_id");
                id = rsId;
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return id;
    }

    //---------------------------------------------------------------------------------------------//
}
