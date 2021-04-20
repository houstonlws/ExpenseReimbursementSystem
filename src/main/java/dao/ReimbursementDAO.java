package dao;

import model.User;
import model.Reimbursement;

import java.util.List;

public interface ReimbursementDAO {

    /**
     * <p>Adds a reimbursement object to the database
     * @param reimbursement - The reimbursement object to be added to the database
     * @return - A true or false value depending on whether or not a reimbursement was added to the database
     */
    boolean addReimbursement(Reimbursement reimbursement);

    /**
     * <p>Deletes a reimbursement object from the database
     * @param reimbursement - The reimbursement object to be removed from the database
     * @return - A true or false value depending on whether or not the reimbursement was successfully deleted
     */
    boolean deleteReimbursement(Reimbursement reimbursement);

    /**
     * <p>Set the values of an existing entry in the database to the values in an updated object
     * @param reimbursement - The reimbursement object being used to store values for the reimbursement
     * @return  - A true or false value depending on whether the update was or was not successful
     */
    boolean updateReimbursement(Reimbursement reimbursement);

    /**
     * <p>Returns a reimbursement object given an id
     * @param id - The id number of the reimbursement to get from the database
     * @return - A reimbursement object containing the data of a reimbursement with a matching id to the parameter
     */
    Reimbursement getReimbursementById(int id);

    /**
     * <p>Gets all reimbursements that were submitted by a particular account
     * @param user - User object that represents the author of the reimbursements
     * @return - An array list of reimbursements for a particular account
     */
    List<Reimbursement> getAccountReimbursements(User user);

    /**
     * <p>Retrieves all reimbursements from database and stores them in a list
     * @return - This method returns an array list of reimbursement objects
     */
    List<Reimbursement> getAllReimbursements();

    /**
     *<p>Gets the id value of a reimbursement type given it's name
     * @param type - The name of the reimbursement type
     * @return - An integer that represents the id of the reimbursement type
     */
    int getIdByType(String type);

    /**
     * <p>A method that gets the name of a reimbursement type given its id
     * @param id - The id number of the Reimbursement type to be retrieved from the database
     * @return - The string name of the reimbursement type
     */
    String getTypeById(int id);

    /**
     * <p>Retrieves the id number of a reimbursement status given its string name
     * @param status - The string name of the reimburement status
     * @return - An integer representing the id number of the reimbursement status
     */
    int getIdByStatus(String status);

    /**
     *<p>Retrieves the string name of a reimbursement status given its id number
     * @param id - The id number of the reimbursement status to br retrieved from the database
     * @return - The string value of the reimbursement status
     */
    String getStatusById(int id);

    /**
     * <p>creates an image file given a byte array and a reimbursement
     * @param bytes - A byte array containing image data to be written to a file
     * @param r - The reimbursement to which the byte array belongs
     */
    void createImage(byte[] bytes, Reimbursement r);

}
