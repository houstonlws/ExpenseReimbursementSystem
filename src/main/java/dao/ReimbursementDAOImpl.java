package dao;

import model.User;
import model.Reimbursement;
import service.ReimbursementServiceDAO;
import utilities.DAOUtilities;
import utilities.Logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAOImpl implements ReimbursementDAO{

    Logging logger = Logging.getInstance();
    String myclass = UserDAOImpl.class.getName();
    Connection connection = null;
    Connection testConnection = null;
    PreparedStatement stmt = null;
    PreparedStatement stmt2 = null;
    UserDAO udao = DAOUtilities.getUserDAO();

    //---------------------------------------------------------------------------------------------//

    @Override
    public boolean addReimbursement(Reimbursement reimbursement) {
        try {
            String sql = "";
            connection = DAOUtilities.getConnection();
            if(reimbursement.getReceipt()!=null){
                sql = "INSERT INTO ers_reimbursement VALUES (DEFAULT,?,CURRENT_TIMESTAMP,NULL,?,?,?, NULL,3,?,?);";
                stmt = connection.prepareStatement(sql);
                User user = udao.getUserByName(reimbursement.getReimbAuthor());
                stmt.setDouble(1, reimbursement.getReimbAmount());
                stmt.setString(2, reimbursement.getReimbDescription());
                stmt.setBytes(3, reimbursement.getReceipt());
                stmt.setInt(4, user.getUserId());
                stmt.setInt(5, getIdByType(reimbursement.getReimbType()));
                stmt.setString(6, reimbursement.getFileFormat());
            }else{
                sql = "INSERT INTO ers_reimbursement VALUES (DEFAULT,?,CURRENT_TIMESTAMP,NULL,?,?,?, NULL,3,?);";
                User user = udao.getUserByName(reimbursement.getReimbAuthor());
                stmt.setDouble(1, reimbursement.getReimbAmount());
                stmt.setString(2, reimbursement.getReimbDescription());
                stmt.setBytes(3, reimbursement.getReceipt());
                stmt.setInt(4, user.getUserId());
                stmt.setInt(5, getIdByType(reimbursement.getReimbType()));

            }


            if(stmt.executeUpdate() != 0){
                logger.info(myclass,"Added reimbursement to database.");
                return true;
            } else {
                logger.info(myclass,"Reimbursement was not added to database.");
                return false;
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass, "Error adding reimbursement to database.", e);
            return false;
        }
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public boolean deleteReimbursement(Reimbursement reimbursement) {
        try {
            connection = DAOUtilities.getConnection();
            String sql = "DELETE FROM ers_reimbursements WHERE reimb_id=?;";
            stmt = connection.prepareStatement(sql);

            stmt.setInt(1, reimbursement.getReimbId());

            if(stmt.executeUpdate() != 0){
                logger.info(myclass,"Reimbursement removed from database.");
                return true;
            } else {
                logger.info(myclass,"Reimbursement was not removed from database..");
                return false;
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass,"Error removing reimbursement from database.", e);
            return false;
        }
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public boolean updateReimbursement(Reimbursement reimbursement) {
        try {
            connection = DAOUtilities.getConnection();
            String sql = "UPDATE ers_reimbursement " +
                    "SET reimb_resolver=?, reimb_resolved=CURRENT_TIMESTAMP, reimb_status_id=? WHERE reimb_id=?;";
            stmt = connection.prepareStatement(sql);

            User user = udao.getUserByName(reimbursement.getReimbResolver());

            stmt.setInt(1, user.getUserId());
            stmt.setInt(2,getIdByStatus(reimbursement.getReimbStatus()));
            stmt.setInt(3, reimbursement.getReimbId());

            if(stmt.executeUpdate() != 0){
                logger.info(myclass,"Reimbursement " + reimbursement.getReimbId() + " was approved.");
                return true;
            } else {
                logger.info(myclass,"Reimbursement " + reimbursement.getReimbId() + "was not approved.");
                return false;
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass,"Error approving reimbursement.", e);
            return false;
        }
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public Reimbursement getReimbursementById(int id) {
        Reimbursement reimbursement = null;
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM reimbursements WHERE reimb_id=?;";
            stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rsId= rs.getInt("reimb_id");
                Double rsAmount = rs.getDouble("reimb_amount");
                String rsSubmitted = rs.getString("reimb_submitted");
                String rsResolved = rs.getString("reimb_resolved");
                String rsDescription = rs.getString("reimb_description");
                byte[] rsReceipt = rs.getBytes("reimb_receipt");
                String rsAuthor = rs.getString("reimb_author");
                String rsResolver = rs.getString("reimb_resolver");
                String rsStatus = rs.getString("reimb_status");
                String rsType = rs.getString("reimb_type");
                String rsFileFormat = rs.getString("receipt_format");
                reimbursement = new Reimbursement();
                reimbursement.setReimbId(rsId);
                reimbursement.setReimbAmount(rsAmount);
                reimbursement.setReimbSubmitted(rsSubmitted);
                reimbursement.setReimbResolved(rsResolved);
                reimbursement.setReimbDescription(rsDescription);
                reimbursement.setReceipt(rsReceipt);
                reimbursement.setReimbAuthor(rsAuthor);
                reimbursement.setReimbResolver(rsResolver);
                reimbursement.setReimbStatus(rsStatus);
                reimbursement.setReimbType(rsType);
                reimbursement.setFileFormat(rsFileFormat);
                if(reimbursement.getReceipt()!=null){
                    createImage(rsReceipt, reimbursement);
                }
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return reimbursement;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public List<Reimbursement> getAccountReimbursements(User user) {
        List<Reimbursement> list = new ArrayList<>();
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM reimbursements WHERE reimb_author=?;";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int rsId= rs.getInt("reimb_id");
                Double rsAmount = rs.getDouble("reimb_amount");
                String rsSubmitted = rs.getString("reimb_submitted");
                String rsResolved = rs.getString("reimb_resolved");
                String rsDescription = rs.getString("reimb_description");
                byte[] rsReceipt = rs.getBytes("reimb_receipt");
                String rsAuthor = rs.getString("reimb_author");
                String rsResolver = rs.getString("reimb_resolver");
                String rsStatus = rs.getString("reimb_status");
                String rsType = rs.getString("reimb_type");
                String rsFileFormat = rs.getString("receipt_format");
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setReimbId(rsId);
                reimbursement.setReimbAmount(rsAmount);
                reimbursement.setReimbSubmitted(rsSubmitted);
                reimbursement.setReimbResolved(rsResolved);
                reimbursement.setReimbDescription(rsDescription);
                reimbursement.setReceipt(rsReceipt);
                reimbursement.setReimbAuthor(rsAuthor);
                reimbursement.setReimbResolver(rsResolver);
                reimbursement.setReimbStatus(rsStatus);
                reimbursement.setReimbType(rsType);
                reimbursement.setFileFormat(rsFileFormat);
                if(reimbursement.getReceipt()!=null){
                    createImage(rsReceipt, reimbursement);
                }
                list.add(reimbursement);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return list;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public List<Reimbursement> getAllReimbursements() {
        List<Reimbursement> list = new ArrayList<>();
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM reimbursements;";
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int rsId= rs.getInt("reimb_id");
                if(rsId==0){
                    continue;
                }
                Double rsAmount = rs.getDouble("reimb_amount");
                String rsSubmitted = rs.getString("reimb_submitted");
                String rsResolved = rs.getString("reimb_resolved");
                String rsDescription = rs.getString("reimb_description");
                byte[] rsReceipt = rs.getBytes("reimb_receipt");
                String rsAuthor = rs.getString("reimb_author");
                String rsResolver = rs.getString("reimb_resolver");
                String rsStatus = rs.getString("reimb_status");
                String rsType = rs.getString("reimb_type");
                String rsFileFormat = rs.getString("receipt_format");
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setReimbId(rsId);
                reimbursement.setReimbAmount(rsAmount);
                reimbursement.setReimbSubmitted(rsSubmitted);
                reimbursement.setReimbResolved(rsResolved);
                reimbursement.setReimbDescription(rsDescription);
                reimbursement.setReceipt(rsReceipt);
                reimbursement.setReimbAuthor(rsAuthor);
                reimbursement.setReimbResolver(rsResolver);
                reimbursement.setReimbStatus(rsStatus);
                reimbursement.setReimbType(rsType);
                reimbursement.setFileFormat(rsFileFormat);
                if(reimbursement.getReceipt()!=null){
                    createImage(rsReceipt, reimbursement);
                }
                list.add(reimbursement);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return list;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public int getIdByType(String type) {
        int id = -1;
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM ers_reimbursement_type WHERE reimb_type=?;";
            stmt2 = connection.prepareStatement(sql);
            stmt2.setString(1, type);
            ResultSet rs = stmt2.executeQuery();

            while(rs.next()){
                id = rs.getInt( "reimb_type_id");
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass,"Error getting type id.", e);
        }
        return id;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public String getTypeById(int id) {
        String type = "";
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM ers_reimbursement_type WHERE reimb_type_id=?;";
            stmt2 = connection.prepareStatement(sql);
            ResultSet rs = stmt2.executeQuery();

            type = rs.getString( "reimb_type");

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass,"Error getting type id.", e);
        }
        return type;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public int getIdByStatus(String status) {
        int id = -1;
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM ers_reimbursement_status WHERE reimb_status=?;";
            stmt2 = connection.prepareStatement(sql);
            stmt2.setString( 1,status);
            ResultSet rs = stmt2.executeQuery();
            while(rs.next()){
                id = rs.getInt( "reimb_status_id");
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass,"Error getting status id.", e);
        }
        return id;    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public String getStatusById(int id) {
        String type = "";
        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM ers_reimbursement_status WHERE reimb_status_id=?;";
            stmt2 = connection.prepareStatement(sql);
            ResultSet rs = stmt2.executeQuery();

            type = rs.getString( "reimb_status");

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            logger.error(myclass,"Error getting status id.", e);
        }
        return type;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public void createImage(byte[] bytes, Reimbursement r) {
        File file = new File("src/main/resources/website/html/api/receipts/"+r.getReimbId()+".jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);

            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //---------------------------------------------------------------------------------------------//


}
