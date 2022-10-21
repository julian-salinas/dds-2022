package presentacion.controladores;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.repositorios.RepositorioOrganizaciones;
import domain.repositorios.RepositorioUsuarios;
import domain.ubicaciones.Ubicacion;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RegistrarOrgController {

  public ModelAndView index(Request request, Response response) {
    String username = request.session().attribute("usuario_signeado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    request.session().attribute("usuario_signeado_literal", user);
    return new ModelAndView(null, "registrarOrg.hbs");
  }

  public ModelAndView post(Request request, Response response) {
    String nombre = request.queryParams("nombre");
    String razonSocial = request.queryParams("razonSocial");
    String tipo = request.queryParams("tipo");
    String clasificacion = request.queryParams("clasificacion");
    String ubicacion = request.queryParams("ubicacion");
    String sector = request.queryParams("sector");

    Organizacion org = new Organizacion(nombre, razonSocial, TipoOrganizacion.valueOf(tipo),
        new Ubicacion(), ClasificacionOrg.valueOf(clasificacion));

    Usuario usuario = request.session().attribute("usuario_signeado_literal");
    usuario.setOrg(org);
    RepositorioUsuarios.getInstance().update(usuario);

    request.session().attribute("usuario_logueado", usuario.getUsername());
    response.redirect("/home");
    return null;
  }

}
