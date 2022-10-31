package presentacion.controladores;

import domain.repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class GuiaController {

  public ModelAndView index(Request request, Response response) {
    String username = request.session().attribute("usuario_logueado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    Object model = user.getMiembro();
    return new ModelAndView(model, "guia.hbs");
  }
}
