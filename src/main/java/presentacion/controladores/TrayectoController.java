package presentacion.controladores;

import domain.repositorios.RepositorioUsuarios;
import domain.trayecto.Trayecto;
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
    return new ModelAndView(null, "tramo.hbs");
  }

  public ModelAndView postCompartido(Request request, Response response) {
    return new ModelAndView(null, "tramo.hbs");
  }

}
