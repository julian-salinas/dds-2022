package presentacion.controladores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {
  // Pagina de Login
  public ModelAndView index(Request request, Response response) {
    return new ModelAndView(null, "login.hbs");
  }

  // Logica para procesar los datos introducidos en la pag. de Login
  public ModelAndView post(Request request, Response response) {
    String usuario = request.queryParams("nombre");
    String password = request.queryParams("password");
    //Usuario usuarioEncontrado = UsuarioRepositorio.get().findByUsername(usuario);

    // Si no existe, vuelvo a la pagina de Login
    /*if (usuarioEncontrado == null ||
        !usuarioEncontrado.getPassword().equals(password)) {
      return new ModelAndView(null, "login.hbs");
    }*/

    // Si existe, redirijo a la pag. q haga falta
    request.session().attribute("usuario_logueado", usuario);
    response.redirect("/capturas");
    return null;
  }
}
