package presentacion.controladores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class InicioController {

  public ModelAndView index(Request request, Response response) {

    return new ModelAndView(null, "inicio.hbs");
  }
}
