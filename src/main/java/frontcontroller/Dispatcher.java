package frontcontroller;

import controller.ReimbursementController;
import controller.UserController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Dispatcher {

    Javalin app;

    public Dispatcher(Javalin app){
        this.app = app;
        allRoutes();
    }

    public void allRoutes(){
        userRoutes();
        adminRoutes();
        webRoutes();
    }

    public void userRoutes(){
        app.routes(() -> {
            path("/api/user", () -> {
                path("/update", () -> {
                    path("/role", () -> {
                        put(UserController::updateUserRole);
                    });
                });
                path("/get", () -> {
                    get(UserController::getUser);
                });
                path("/reimbursements", () -> {
                    path("/all", () -> {
                        get(ReimbursementController::getAllAccountReimbursements);
                    });
                    path("/approved", () -> {
                        get(ReimbursementController::getApprovedAccountReimbursements);
                    });
                    path("/denied", () -> {
                        get(ReimbursementController::getDeniedAccountReimbursements);
                    });
                    path("/pending", () -> {
                        get(ReimbursementController::getPendingAccountReimbursements);
                    });
                    path("/add", () -> {
                        post(ReimbursementController::addReimbursement);
                    });
                });
            });
        });
    }


    public void adminRoutes(){
        app.routes(() -> {
            path("/api/admin", () -> {
                path("/get", () -> {
                    get(UserController::getUser);
                });
                path("/getall", () -> {
                    get(UserController::getAllUsers);
                });
                path("/reimbursements", () -> {
                    path("/all", () -> {
                        get(ReimbursementController::getAllReimbursements);
                    });
                    path("/approved", () -> {
                        get(ReimbursementController::getApprovedReimbursements);
                    });
                    path("/denied", () -> {
                        get(ReimbursementController::getDeniedReimbursements);
                    });
                    path("/pending", () -> {
                        get(ReimbursementController::getPendingReimbursements);
                    });
                    path("/update", () -> {
                        put(ReimbursementController::updateReimbursement);
                    });
                });
            });
        });
    }


    public void webRoutes(){
        app.routes(() -> {
            path("/login", () -> {
                post(UserController::login);
            });
            path("/register", () -> {
                post(UserController::register);
            });
            path("/api/logout", () -> {
                post(UserController::logout);
            });
        });
    }
}
