package presentacion;

import presentacion.controladores.*;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.beans.Transient;

public class Router {
  public static void configure() {
    HandlebarsTemplateEngine engineTemplate = new HandlebarsTemplateEngine();

    // Todos los controllers
    SigninController signinController = new SigninController();
    LoginController loginController = new LoginController();
    InicioController inicioController = new InicioController();
    HomeController homeController = new HomeController();
    GuiaController guiaController = new GuiaController();
    TrayectoController trayectoController = new TrayectoController();

    //DebugScreen.enableDebugScreen();

    Spark.staticFiles.location("public");

    // Todos los requests
   Spark.get("/signin", signinController::index, engineTemplate);
   Spark.post("/signin", signinController::post, engineTemplate);
   Spark.get("/login", loginController::index, engineTemplate);
   Spark.post("/login", loginController::post, engineTemplate);


   Spark.get("/inicio", inicioController::index, engineTemplate);
   Spark.get("/home", homeController::index, engineTemplate);
   Spark.get("/guia", guiaController::index, engineTemplate);
   Spark.get("/trayecto", trayectoController::index, engineTemplate);
   Spark.post("/trayecto", trayectoController::post, engineTemplate);
   Spark.post("/trayectoCompartido", trayectoController::postCompartido, engineTemplate);
  }
}
