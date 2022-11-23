package presentacion.controladores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class InicioController {

  public ModelAndView index(Request request, Response response) {

    Map<String, Object> model = new HashMap<>();
    model.put("inicio", true);
    return new ModelAndView(model, "inicio.hbs");
  }
}
