package dao;

import model.Reimbursement;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.DAOUtilities;
import utilities.Testing;

import java.util.List;

class ReimbursementDAOImplTest {

    ReimbursementDAO dao = DAOUtilities.getReimbursementDAO();
    int reimbId = 30;
    double reimbAmount = 100.00;
    String reimbSubmitted = "2021-04-01";
    String reimbResolved = "2021-04-01";
    String reimbDescription = "desc";
    byte[] receipt = null;
    String reimbAuthor = "test";
    String reimbResolver = "test2";
    String reimbStatus = "Pending";
    String reimbType = "Lodging";
    String fileFormat = "jpg";
    Reimbursement r = new Reimbursement();
    UserDAO dao2 = DAOUtilities.getUserDAO();

    @BeforeEach
    void setUp() {
        Testing.h2Init();
    }

    @AfterEach
    void tearDown() {
        Testing.h2Destroy();
    }

    @Test
    void addReimbursement() {
        boolean b = dao.addReimbursement(r);
        System.out.println(b);
    }

    @Test
    void deleteReimbursement() {
        Reimbursement r = dao.getReimbursementById(1);
        boolean a = dao.deleteReimbursement(r);
        System.out.println(a);
    }

    @Test
    void updateReimbursement() {
        Reimbursement rb = dao.getReimbursementById(1);
        System.out.println(rb);
        rb = dao.getReimbursementById(1);
        System.out.println(rb);
    }

    @Test
    void getReimbursementById() {
        Reimbursement r = dao.getReimbursementById(1);
        System.out.println(r);
    }

    @Test
    void getAccountReimbursements() {
        User user = dao2.getUserById(2);
        List<Reimbursement> list = dao.getAccountReimbursements(user);
        System.out.println(list);
    }

    @Test
    void approveReimbursement() {
        User user = dao2.getUserById(4);
        Reimbursement rb = dao.getReimbursementById(1);

    }

    @Test
    void denyReimbursement() {
        User user = dao2.getUserById(4);
        Reimbursement rb = dao.getReimbursementById(1);

    }
}