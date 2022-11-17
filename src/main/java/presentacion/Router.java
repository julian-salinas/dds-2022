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
    RegistrarAgSecController registrarAgSecController = new RegistrarAgSecController();
    SectoresController sectoresController = new SectoresController();
    HcController hcController = new HcController();
    AceptarMiembroController aceptarMiembroController = new AceptarMiembroController();
    PedirVinculacionController pedirVinculacionController = new PedirVinculacionController();
    MedicionesController medicionesController = new MedicionesController();
    CargarParadaController cargarParadaController = new CargarParadaController();
    CargarLineaController cargarLineaController = new CargarLineaController();
    CargarFeController cargarFeController = new CargarFeController();


    //DebugScreen.enableDebugScreen();

    Spark.staticFiles.location("public");

    // TODO: Mejorar las rutas/paths

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
    Spark.post("/hc-miembro",hcController::postMiembro,engineTemplate);

    // Organizacion
    Spark.get("/sectores", sectoresController::index, engineTemplate);
    Spark.post("/sectores", sectoresController::post, engineTemplate);
    Spark.get("/aceptar-miembros", aceptarMiembroController::index, engineTemplate);
    Spark.post("/aceptar-miembros", aceptarMiembroController::post, engineTemplate);
    Spark.get("/hc-org", hcController::index, engineTemplate);
    Spark.post("/hc-org", hcController::post, engineTemplate);
    Spark.get("/mediciones", medicionesController::index, engineTemplate);
    Spark.post("/mediciones-csv", medicionesController::postCsv, engineTemplate);
    Spark.post("/mediciones-manual", medicionesController::postManual, engineTemplate);
    Spark.post("/cargar-fe", medicionesController::postFe, engineTemplate);

    // Agente Sectorial
    Spark.get("/registrarAgSec", registrarAgSecController::redirect, engineTemplate);
    Spark.get("/registrarAgSec/tipo", registrarAgSecController::index, engineTemplate);
    Spark.post("/registrarAgSec/tipo", registrarAgSecController::post, engineTemplate);
    Spark.get("/registrarAgSec/sector", registrarAgSecController::index_sector, engineTemplate);
    Spark.post("/registrarAgSec/sector", registrarAgSecController::post_sector, engineTemplate);
    Spark.get("/hc-agente", hcController::index_agente, engineTemplate);
    Spark.post("/hc-agente", hcController::post_agente, engineTemplate);

    // Admin
    Spark.get("/cargarParada", cargarParadaController::index, engineTemplate);
    Spark.post("/nuevaParada", cargarParadaController::post, engineTemplate);
    Spark.get("/cargarLinea", cargarLineaController::index, engineTemplate);
    Spark.post("/nuevaLinea", cargarLineaController::post, engineTemplate);
    Spark.get("/cargarFe", cargarFeController::index, engineTemplate);
    Spark.post("/nuevaFe", cargarFeController::post, engineTemplate);

  }
}
