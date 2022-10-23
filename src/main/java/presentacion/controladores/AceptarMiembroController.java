package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AceptarMiembroController {

  public ModelAndView index(Request request, Response response) {
    String username = request.cookie("username");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();
    // TODO: probablemente haya q pasarle un map para q no rompa
    return new ModelAndView(org, "aceptarMiembros.hbs");
  }

}
