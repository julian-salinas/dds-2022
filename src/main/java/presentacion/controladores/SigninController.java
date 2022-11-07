package presentacion.controladores;

import domain.contrasenias.Validador;
import domain.contrasenias.excepciones.PasswordException;
import repositorios.RepositorioUsuarios;
import presentacion.TipoUsuario;
import presentacion.Usuario;
import presentacion.errores.Error;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SigninController {

  public ModelAndView index(Request request, Response response) {
    return new ModelAndView(null, "signin.hbs");
  }

  public ModelAndView post(Request request, Response response) {
    String username = request.queryParams("nombre");
    String password = request.queryParams("password");
    TipoUsuario tipo = TipoUsuario.valueOf(request.queryParams("tipo"));

    Error error = new Error();

    // Valido Username
    if(RepositorioUsuarios.getInstance().findByUsername(username) != null) {
      error.setError(true);
      error.setDescripcion("Ya existe un usuario con ese username. Pruebe uno diferente. \n");
      return new ModelAndView(error, "signin.hbs");
    }

    // Valido Contraseña
    Validador validador = new Validador();
    try {
      validador.validarContrasenia(password);
    } catch (PasswordException e) {
      error.setError(true);
      error.setDescripcion(e.getMessage());
      return new ModelAndView(error, "signin.hbs");
    }

    Usuario usuario = new Usuario(username, password, tipo);
    RepositorioUsuarios.getInstance().add(usuario);
    request.session().attribute("usuario_logueado", username);

    if(tipo == TipoUsuario.MIEMBRO)
      response.redirect("/registrarMiembro");
    if(tipo == TipoUsuario.ORGANIZACION)
      response.redirect("/registrarOrg");
    if(tipo == TipoUsuario.AGENTE_SECTORIAL)
      response.redirect("/registrarAgSec");
    if(tipo == TipoUsuario.ADMINISTRADOR)
      response.redirect("/home");

    response.redirect("/inicio");
    return null;
  }
}
