package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.repositorios.RepositorioOrganizaciones;
import domain.repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedirVinculacionController {

  public ModelAndView index(Request request, Response response) {
//    String username = request.cookie("username");
//    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    List<Organizacion> organizaciones = RepositorioOrganizaciones.getInstance().all();
    Map<String, Object> model = new HashMap<>();
    model.put("organizaciones", organizaciones);
    return new ModelAndView(model, "vincularAOrg.hbs");
  }

}
