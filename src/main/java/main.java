import frontcontroller.FrontController;
import io.javalin.Javalin;

public class main {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config-> {
            config.addStaticFiles("/website");
            config.addSinglePageRoot("/api/user", "/website/html/api/user/dashboard.html");
            config.addSinglePageRoot("/api/admin", "/website/html/api/admin/dashboard.html");
        }).start(9001);
        FrontController fc = new FrontController(app);
    }
}
