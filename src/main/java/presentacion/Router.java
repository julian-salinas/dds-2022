package presentacion;

import presentacion.controladores.HomeController;
import presentacion.controladores.InicioController;
import presentacion.controladores.LoginController;
import presentacion.controladores.SigninController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {
  public static void configure() {
    HandlebarsTemplateEngine engineTemplate = new HandlebarsTemplateEngine();

    // Todos los controllers
    SigninController signinController = new SigninController();
    LoginController loginController = new LoginController();
    InicioController inicioController = new InicioController();
    HomeController homeController = new HomeController();

    //DebugScreen.enableDebugScreen();

    Spark.staticFiles.location("public");

    // Todos los requests
   Spark.get("/signin", signinController::index, engineTemplate);
   Spark.post("/signin", signinController::post, engineTemplate);
   Spark.get("/login", loginController::index, engineTemplate);
   Spark.post("/login", loginController::post, engineTemplate);


   Spark.get("/inicio", inicioController::index, engineTemplate);
   Spark.get("/home", homeController::index, engineTemplate);
  }
}
