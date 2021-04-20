package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Testing{

    public static String URL = "jdbc:h2:./src";
    public static String CONNECTION_USERNAME = "sa";
    public static String CONNECTION_PASSWORD = "sa";
    private static Connection testConnection;

    public static Connection getConnection(){
        try {
            if (testConnection==null){
                System.out.println("Opening new connection...");
                testConnection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testConnection;
    }

    public static void h2Init() {
        try{
            testConnection = getConnection();

            String sql =
                    "CREATE VIEW reimbursements AS(\n" +
                            "\tSELECT er.reimb_id\n" +
                            "\t, er.reimb_amount\n" +
                            "\t, er.reimb_submitted\n" +
                            "\t, er.reimb_resolved\n" +
                            "\t, er.reimb_description\n" +
                            "\t, er.reimb_receipt\n" +
                            "\t, eu.ers_username reimb_author\n" +
                            "\t, u.ers_username reimb_resolver\n" +
                            "\t, ert.reimb_type\n" +
                            "\t, ers.reimb_status \n" +
                            "\tFROM ers_reimbursement er\n" +
                            "\tINNER JOIN ers_users eu ON er.reimb_author = eu.ers_user_id\n" +
                            "\tFULL OUTER JOIN ers_reimbursement_type ert ON er.reimb_type_id = ert.reimb_type_id\n" +
                            "\tFULL OUTER JOIN ers_reimbursement_status ers ON er.reimb_status_id = ers.reimb_status_id\n" +
                            "\tLEFT JOIN ers_users u ON er.reimb_resolver = u.ers_user_id\n" +
                            "\tORDER BY er.reimb_id ASC\n" +
                            ");\n" +
                            "\n" +
                            "CREATE VIEW users AS(\n" +
                            "\tSELECT eu.ers_user_id, \n" +
                            "\teu.ers_username, \n" +
                            "\teu.ers_password, \n" +
                            "\teu.user_first_name, \n" +
                            "\teu.user_last_name, \n" +
                            "\teu.user_email, \n" +
                            "\teur.user_role \n" +
                            "\tFROM ers_users eu, ers_user_roles eur\n" +
                            "\tWHERE eu.ers_user_role_id = eur.ers_user_role_id\n" +
                            ");\n" +
                            "\n" +
                            "CREATE TABLE ers_user_roles(\n" +
                            "\ters_user_role_id INTEGER PRIMARY KEY\n" +
                            "\t, user_role varchar(50) NOT NULL UNIQUE\n" +
                            ");\n" +
                            "\n" +
                            "CREATE TABLE ers_reimbursement_type(\n" +
                            "\treimb_type_id INTEGER PRIMARY KEY\n" +
                            "\t,reimb_type varchar(60) NOT NULL UNIQUE\n" +
                            ");\n" +
                            "\n" +
                            "CREATE TABLE ers_reimbursement_status(\n" +
                            "\treimb_status_id INTEGER PRIMARY KEY\n" +
                            "\t, reimb_status varchar(50) NOT NULL UNIQUE\n" +
                            ");\n" +
                            "\n" +
                            "CREATE TABLE ers_users(\n" +
                            "\ters_user_id SERIAL PRIMARY KEY\n" +
                            "\t, ers_username varchar(50) NOT NULL UNIQUE\n" +
                            "\t, ers_password varchar(200) NOT NULL\n" +
                            "\t, user_first_name varchar(50) NOT NULL\n" +
                            "\t, user_last_name varchar(50) NOT NULL\n" +
                            "\t, user_email varchar(100) NOT NULL UNIQUE\n" +
                            "\t, ers_user_role_id INTEGER NOT NULL\n" +
                            "\t, CONSTRAINT user_role_fk \n" +
                            "\tFOREIGN KEY (ers_user_role_id)\n" +
                            "\tREFERENCES ers_user_roles(ers_user_role_id)\n" +
                            "\tON DELETE CASCADE\n" +
                            ");\n" +
                            "\n" +
                            "CREATE TABLE ers_reimbursement(\n" +
                            "\treimb_id SERIAL PRIMARY KEY\n" +
                            "\t, reimb_amount DECIMAL NOT NULL\n" +
                            "\t, reimb_submitted DATE NOT NULL\n" +
                            "\t, reimb_resolved DATE\n" +
                            "\t, reimb_description varchar(300)\n" +
                            "\t, reimb_receipt BYTEA \n" +
                            "\t, reimb_author INTEGER NOT NULL\n" +
                            "\t, reimb_resolver INTEGER\n" +
                            "\t, reimb_status_id INTEGER NOT NULL\n" +
                            "\t, reimb_type_id INTEGER NOT NULL\n" +
                            "\t, receipt_format varchar(10)\n" +
                            "\t, CONSTRAINT ers_user_fk_auth FOREIGN KEY (reimb_author)\n" +
                            "\tREFERENCES ers_users(ers_user_id) ON DELETE CASCADE\n" +
                            "\t, CONSTRAINT ers_user_fk_reslvr FOREIGN KEY (reimb_resolver)\n" +
                            "\tREFERENCES ers_users(ers_user_id) ON DELETE CASCADE\n" +
                            "\t, CONSTRAINT ers_reimbursement_status_fk FOREIGN KEY (reimb_status_id)\n" +
                            "\tREFERENCES ers_reimbursement_status(reimb_status_id) ON DELETE CASCADE\n" +
                            "\t, CONSTRAINT ers_reimbursement_type_fk FOREIGN KEY (reimb_type_id)\n" +
                            "\tREFERENCES ers_reimbursement_type(reimb_type_id) ON DELETE CASCADE\n" +
                            ");\n";

            Statement state = testConnection.createStatement();
            state.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void h2Destroy() {
        try{
            testConnection = getConnection();

            String sql = "DROP VIEW IF EXISTS reimbursements;\n" +
                    "DROP VIEW IF EXISTS users;\n" +
                    "DROP TABLE IF EXISTS ers_reimbursement;\n" +
                    "DROP TABLE IF EXISTS ers_users;\n" +
                    "DROP TABLE IF EXISTS ers_reimbursement_type;\n" +
                    "DROP TABLE IF EXISTS ers_reimbursement_status;\n" +
                    "DROP TABLE IF EXISTS ers_user_roles;;";

            Statement state = testConnection.createStatement();
            state.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
