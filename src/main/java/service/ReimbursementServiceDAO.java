package service;

import model.Reimbursement;
import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ReimbursementServiceDAO {

    /**
     * <p> Filters reimbursements from account to get a list of approved requests
     * @param user - The account to get reimbursements for
     * @return An array list of reimbursement objects
     */
    List<Reimbursement> getAccountApproved(User user);

    /**
     * <p> Filters reimbursements to get a list of denied requests for a particular account
     * @param user - The account to get the reimbursements for
     * @ An array list of Reimbursement objectts
     */
    List<Reimbursement> getAccountDenied(User user);

    /**
     * <p> Filters account reimbursements to get a list of pending reimbursements
     * @param user - The account to get reimbursements for
     * @return An arraylist of reimbursements
     */
    List<Reimbursement> getAccountPending(User user);

    /**
     * <p> Gets a list of all approved reimbursements
     * @return An arraylist of approved reimbursements
     */
    List<Reimbursement> getApproved();

    /**
     * <p> Gets a list of all denied reimbursements
     * @return An arraylist of reimbursements
     */
    List<Reimbursement> getDenied();

    /**
     * <p>Gets a list of all pending reimbursements
     * @return An arraylist of reimbursements
     */
    List<Reimbursement> getPending();

    /**
     * <p> Converts an input stream to a byte array
     * @param file - The input stream object to convert to a byte array
     * @return A byte array of image data
     * @throws IOException
     */
    byte[] convertFile(InputStream file) throws IOException;
}
