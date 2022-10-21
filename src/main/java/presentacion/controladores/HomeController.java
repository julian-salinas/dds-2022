package presentacion.controladores;

import domain.repositorios.RepositorioUsuarios;
import presentacion.TipoUsuario;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {

  public ModelAndView index(Request request, Response response) {

    String username = request.session().attribute("usuario_logueado");
    response.cookie("username", username);
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);

    if (user.getTipo().equals(TipoUsuario.ORGANIZACION)) {
      /*Map<String, Object> model = new HashMap<>();
      model.put("org", user.getOrg());*/
      Object model = user.getOrg();
      return new ModelAndView(model, "homeOrganizacion.hbs");
    } else if (user.getTipo().equals(TipoUsuario.MIEMBRO)) {
      Object model = user.getMiembro();
      return new ModelAndView(model, "homeMiembro.hbs");
    }
    else
      return null;

  }

}
