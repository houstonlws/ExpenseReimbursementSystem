package service;

import dao.UserDAO;
import model.User;
import utilities.DAOUtilities;

public class UserServiceDAOImpl implements UserServiceDAO {

    UserDAO dao = DAOUtilities.getUserDAO();

    @Override
    public boolean login(String username, String password) {
        if(dao.verifyUsername(username) && dao.verifyPassword(username, password)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean isAdmin(User user) {
        if(user.getUserRole().equals("Finance Manager")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateUserRole(int userId, String role) {
        User user = dao.getUserById(userId);
        user.setUserRole(role);
        if(dao.updateUser(user)){
            return true;
        }else{
            return false;
        }
    }


}
