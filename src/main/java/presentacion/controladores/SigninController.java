package presentacion.controladores;

import domain.contrasenias.Contrasenia;
import domain.repositorios.RepositorioUsuarios;
import javassist.expr.NewArray;
import presentacion.TipoUsuario;
import presentacion.Usuario;
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

    Contrasenia validador = new Contrasenia();

//    try {
//        validador.validarContrasenia(password);
//    } catch {
//
//    }
    if(RepositorioUsuarios.getInstance().findByUsername(username) != null)
      return new ModelAndView(null, "signin.hbs");

    Usuario usuario = new Usuario(username, password, tipo);

    RepositorioUsuarios.getInstance().add(usuario);
    request.session().attribute("usuario_signeado", username);

    if(tipo == TipoUsuario.MIEMBRO)
      response.redirect("/inicio");
    if(tipo == TipoUsuario.ORGANIZACION)
      response.redirect("/registrarOrg");


    response.redirect("/inicio");
    return null;

  }
}
