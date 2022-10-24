package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HcController {

  public ModelAndView index(Request request, Response response) {

    String username = request.cookie("username");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);

    Organizacion organizacion = user.getOrg();

    Map<String, Object> model = new HashMap<>();
    model.put("mensual", organizacion.hcMensual().enKgCO2());
    model.put("anual", organizacion.hcAnual().enKgCO2());

    return new ModelAndView(model, "hc.hbs");
  }

}
