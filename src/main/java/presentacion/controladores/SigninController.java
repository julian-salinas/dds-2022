package presentacion.controladores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SigninController {
  public ModelAndView index(Request request, Response response) {
    return new ModelAndView(null, "signin.hbs");
  }
  public ModelAndView post(Request request, Response response) {
    // Vacio por ahora
    return new ModelAndView(null, "signin.hbs");
  }
}
