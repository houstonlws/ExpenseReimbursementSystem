package controller;

import dao.ReimbursementDAO;
import dao.UserDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.Reimbursement;
import model.User;
import service.ReimbursementServiceDAO;
import utilities.DAOUtilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

public class ReimbursementController {

    Javalin app;
    static ReimbursementDAO rdao = DAOUtilities.getReimbursementDAO();
    static ReimbursementServiceDAO rsdao = DAOUtilities.getReimbursementServiceDAO();    static UserDAO udao = DAOUtilities.getUserDAO();

    //---------------------------------------------------------------------------------------------//

    public ReimbursementController(Javalin app) {
        this.app = app;
    }

    //---------------------------------------------------------------------------------------------//

    public static void addReimbursement(Context context){
        try {
            User user = context.sessionAttribute("user");
            double reimbAmount = Double.parseDouble(context.formParam("reimbAmount"));
            String reimbDescription = context.formParam("reimbDescription");
            InputStream is = context.uploadedFile("receipt").getContent();
            String fileFormat = context.formParam("file-name");
            byte[] receipt = rsdao.convertFile(is);
            String type = context.formParam("reimbType");
            Reimbursement r = new Reimbursement();
            r.setReimbAmount(reimbAmount);
            r.setReimbDescription(reimbDescription);
            r.setReceipt(receipt);
            r.setReimbAuthor(user.getUsername());
            r.setReimbType(type);
            r.setFileFormat(fileFormat);

            if(rdao.addReimbursement(r)){
                context.result("Request Submitted Successfully!");
            }else{
                context.result("Error Submitting Request!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------------------------------------------------//

    public static void deleteReimbursement(Context context){

    }

    //---------------------------------------------------------------------------------------------//

    public static void updateReimbursement(Context context){
        User user = context.sessionAttribute("user");
        Reimbursement r = context.bodyAsClass(Reimbursement.class);
        r.setReimbResolver(user.getUsername());
        if(rdao.updateReimbursement(r)){
            context.result("true");
        }else{
            context.result("false");
        }
    }

    //---------------------------------------------------------------------------------------------//

    public static void getReimbursementById(Context context){
        int id = Integer.parseInt(context.pathParam("myid"));
        List<Reimbursement> list = rdao.getAllReimbursements();
        context.json(list.get(id));
    }

    //---------------------------------------------------------------------------------------------//

    public static void getAllAccountReimbursements(Context context){
        User user = context.sessionAttribute("user");
        List<Reimbursement> list = rdao.getAccountReimbursements(user);
        context.json(list);
    }

    //---------------------------------------------------------------------------------------------//

    public static void getApprovedAccountReimbursements(Context context){
        User user = context.sessionAttribute("user");
        List<Reimbursement> list = rsdao.getAccountApproved(user);
        context.json(list);
    }

    //---------------------------------------------------------------------------------------------//

    public static void getDeniedAccountReimbursements(Context context){
        User user = context.sessionAttribute("user");
        List<Reimbursement> list = rsdao.getAccountDenied(user);
        context.json(list);
    }

    //---------------------------------------------------------------------------------------------//

    public static void getPendingAccountReimbursements(Context context){
        User user = context.sessionAttribute("user");
        List<Reimbursement> list = rsdao.getAccountPending(user);
        context.json(list);
    }

    //---------------------------------------------------------------------------------------------//

    public static void getAllReimbursements(Context context) {
        List<Reimbursement> list = rdao.getAllReimbursements();
        context.json(list);
    }

    //---------------------------------------------------------------------------------------------//

    public static void getApprovedReimbursements(Context context) {
        List<Reimbursement> list = rsdao.getApproved();
        context.json(list);
    }

    //---------------------------------------------------------------------------------------------//

    public static void getDeniedReimbursements(Context context) {
        List<Reimbursement> list = rsdao.getDenied();
        context.json(list);
    }

    //---------------------------------------------------------------------------------------------//

    public static void getPendingReimbursements(Context context) {
        List<Reimbursement> list = rsdao.getPending();
        context.json(list);
    }

    //---------------------------------------------------------------------------------------------//

}
