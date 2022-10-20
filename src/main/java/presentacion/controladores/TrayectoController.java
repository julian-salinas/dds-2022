package presentacion.controladores;

import domain.repositorios.RepositorioUsuarios;
import presentacion.TipoUsuario;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class TrayectoController {

  public ModelAndView index(Request request, Response response) {

    return new ModelAndView(null, "trayecto.hbs");
  }

  public ModelAndView post(Request request, Response response) {
    return new ModelAndView(null, "guia.hbs");
  }

}
