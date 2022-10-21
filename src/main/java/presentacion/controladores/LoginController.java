package presentacion.controladores;

import domain.repositorios.RepositorioUsuarios;
import presentacion.errores.LogInError;
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

    // Si no existe, vuelvo a la pagina de Login
    if (usuarioEncontrado == null ||
        !usuarioEncontrado.getPassword().equals(password)) {
      LogInError error = new LogInError("Nombre de Usuario o Contraseña incorrectos \n");
      error.setError(true);
      return new ModelAndView(error, "login.hbs");
    }

    // Si existe, redirijo a la pag. q haga falta
    request.session().attribute("usuario_logueado", username);
    response.redirect("/home");
    return null;
  }
}
