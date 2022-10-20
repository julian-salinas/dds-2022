package presentacion.controladores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RegistrarOrgController {

  public ModelAndView index(Request request, Response response) {
    return new ModelAndView(null, "registrarOrg.hbs");
  }

  public ModelAndView post(Request request, Response response) {
    // TODO
    String nombre = request.queryParams("nombre");
    String razonSocial = request.queryParams("razonSocial");
    String clasificacion = request.queryParams("clasificacion");
    String ubicacion = request.queryParams("ubicacion");
    String sector = request.queryParams("sector");



    return new ModelAndView(null, "registrarOrg.hbs");
  }

}
