package utilities;

import dao.*;
import service.ReimbursementServiceDAO;
import service.ReimbursementServiceDAOImpl;
import service.UserServiceDAO;
import service.UserServiceDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOUtilities {

    private static final String CONNECTION_USERNAME = "houston";
    private static final String CONNECTION_PASSWORD = "p4ssw0rd";
    private static final String URL = "jdbc:postgresql://dbhouston.clfznpef1bdn.us-east-2.rds.amazonaws.com:5432/project1";

    private static Connection connection;

        public static synchronized Connection getConnection() throws SQLException {
            if (connection == null) {
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException e) {
                    System.out.println("Could not register driver!");
                    e.printStackTrace();
                }
                connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
            }

            if (connection.isClosed()){
                System.out.println("Opening new connection...");
                connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
            }
            return connection;
        }

        public static void closeConnection() throws SQLException {
            connection.close();
        }

        public static UserDAO getUserDAO(){
            return new UserDAOImpl();
        }
        public static ReimbursementDAO getReimbursementDAO(){ return new ReimbursementDAOImpl(); }

        public static UserServiceDAO getUserServiceDAO(){return new UserServiceDAOImpl(); }
        public static ReimbursementServiceDAO getReimbursementServiceDAO(){
            return new ReimbursementServiceDAOImpl();
        }
}
