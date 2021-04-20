package frontcontroller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import model.User;

public class FrontController {

    Javalin app;
    Dispatcher dispatcher;

    public FrontController(Javalin app) {
        this.app = app;
        dispatcher = new Dispatcher(app);
        app.before("/html/*", FrontController::checkAllRequests);
        app.before("/api/*", FrontController::checkAllRequests);
        app.before("/api/admin*", FrontController::checkIfAdmin);
    }

    private static void checkIfAdmin(Context context) {
        User user = context.sessionAttribute("user");
        if(user.getUserRole().equals("Employee")){
            context.redirect("/api/user");
        }
    }

    public static void checkAllRequests(Context context) {
        if(context.sessionAttribute("user")==null){
            context.redirect("/");
        }
    }

}
