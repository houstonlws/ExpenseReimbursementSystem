package service;

import dao.ReimbursementDAO;
import io.javalin.http.UploadedFile;
import model.Reimbursement;
import model.User;
import utilities.DAOUtilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementServiceDAOImpl implements ReimbursementServiceDAO{

    ReimbursementDAO dao = DAOUtilities.getReimbursementDAO();

    //---------------------------------------------------------------------------------------------//

    @Override
    public List<Reimbursement> getAccountApproved(User user) {
        List<Reimbursement> all = dao.getAccountReimbursements(user);
        List<Reimbursement> list = new ArrayList<>();
        for(Reimbursement r : all){
            if(r.getReimbStatus().equals(("Approved"))){
                list.add(r);
            }else{
                continue;
            }
        }
        return list;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public List<Reimbursement> getAccountDenied(User user) {
        List<Reimbursement> all = dao.getAccountReimbursements(user);
        List<Reimbursement> list = new ArrayList<>();
        for(Reimbursement r : all){
            if(r.getReimbStatus().equals(("Denied"))){
                list.add(r);
            }else{
                continue;
            }
        }
        return list;    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public List<Reimbursement> getAccountPending(User user) {
        List<Reimbursement> all = dao.getAccountReimbursements(user);
        List<Reimbursement> list = new ArrayList<>();
        for(Reimbursement r : all){
            if(r.getReimbStatus().equals(("Pending"))){
                list.add(r);
            }else{
                continue;
            }
        }
        return list;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public List<Reimbursement> getApproved() {
        List<Reimbursement> all = dao.getAllReimbursements();
        List<Reimbursement> list = new ArrayList<>();
        for(Reimbursement r : all){
            if(r.getReimbStatus().equals(("Approved"))){
                list.add(r);
            }else{
                continue;
            }
        }
        return list;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public List<Reimbursement> getDenied() {
        List<Reimbursement> all = dao.getAllReimbursements();
        List<Reimbursement> list = new ArrayList<>();
        for(Reimbursement r : all){
            if(r.getReimbStatus().equals(("Denied"))){
                list.add(r);
            }else{
                continue;
            }
        }
        return list;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public List<Reimbursement> getPending() {
        List<Reimbursement> all = dao.getAllReimbursements();
        List<Reimbursement> list = new ArrayList<>();
        for(Reimbursement r : all){
            if(r.getReimbStatus().equals(("Pending"))){
                list.add(r);
            }else{
                continue;
            }
        }
        return list;
    }

    //---------------------------------------------------------------------------------------------//

    @Override
    public byte[] convertFile(InputStream file) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = file.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] byteArray = buffer.toByteArray();
        return byteArray;
    }

    //---------------------------------------------------------------------------------------------//



}
