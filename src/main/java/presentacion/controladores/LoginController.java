package presentacion.controladores;

import repositorios.RepositorioUsuarios;
import presentacion.errores.Error;
import presentacion.Usuario;
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

    String username = request.queryParams("nombre");
    String password = request.queryParams("password");
    Usuario usuarioEncontrado = RepositorioUsuarios.getInstance().findByUsername(username);

    // Valido que exista
    if (usuarioEncontrado == null ||
        !usuarioEncontrado.getPassword().equals(password)) {
      Error error = new Error("Nombre de Usuario o Contraseña incorrectos \n");
      error.setError(true);
      return new ModelAndView(error, "login.hbs");
    }

    // Si existe, redirijo a la pag. q haga falta
    request.session().attribute("usuario_logueado", username);
    response.redirect("/home");
    return null;
  }
}
