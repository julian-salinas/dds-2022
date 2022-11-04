package presentacion;

import presentacion.controladores.*;
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
    GuiaController guiaController = new GuiaController();
    TrayectoController trayectoController = new TrayectoController();
    RegistrarOrgController registrarOrgController = new RegistrarOrgController();
    RegistrarMiembroController registrarMiembroController = new RegistrarMiembroController();
    SectoresController sectoresController = new SectoresController();
    HcController hcController = new HcController();
    AceptarMiembroController aceptarMiembroController = new AceptarMiembroController();
    PedirVinculacionController pedirVinculacionController = new PedirVinculacionController();
    MedicionesController medicionesController = new MedicionesController();


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

    Spark.get("/registrarOrg", registrarOrgController::index, engineTemplate);
    Spark.post("/registrarOrg", registrarOrgController::post, engineTemplate);
    Spark.get("/registrarMiembro", registrarMiembroController::index, engineTemplate);
    Spark.post("/registrarMiembro", registrarMiembroController::post, engineTemplate);


    // Miembro
    Spark.get("/trayecto", trayectoController::index, engineTemplate);
    Spark.post("/trayecto", trayectoController::post, engineTemplate);
    Spark.post("/agregarMiembro", trayectoController::agregarMiembros, engineTemplate);
    Spark.post("/agregarTramo", trayectoController::agregarTramo, engineTemplate);
    Spark.get("/vincularse", pedirVinculacionController::index, engineTemplate);
    Spark.post("/vincularse", pedirVinculacionController::postOrg, engineTemplate);
    Spark.post("/mandar-postulamiento", pedirVinculacionController::mandar, engineTemplate);
    Spark.get("/hc-miembro",hcController::indexMiembro,engineTemplate);
    Spark.post("/calculo-hc-miembro",hcController::postMiembro,engineTemplate);

    // Organizacion
    Spark.get("/sectores", sectoresController::index, engineTemplate);
    Spark.post("/sectores", sectoresController::post, engineTemplate);
    Spark.get("/aceptar-miembros", aceptarMiembroController::index, engineTemplate);
    Spark.post("/aceptar-miembros", aceptarMiembroController::post, engineTemplate);
    Spark.get("/hc", hcController::index, engineTemplate);
    Spark.post("/calculo-hc", hcController::post, engineTemplate);
    Spark.get("/mediciones", medicionesController::index, engineTemplate);
    Spark.post("/mediciones-csv", medicionesController::postCsv, engineTemplate);
    Spark.post("/mediciones-manual", medicionesController::postManual, engineTemplate);
    Spark.post("/cargar-fe", medicionesController::postFe, engineTemplate);

  }
}
