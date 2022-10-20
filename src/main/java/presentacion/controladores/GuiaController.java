package presentacion.controladores;

import domain.repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class GuiaController {

  public ModelAndView index(Request request, Response response) {

    String username = request.session().attribute("usuario-logueado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);


    return new ModelAndView(user, "guia.hbs");
  }

}
