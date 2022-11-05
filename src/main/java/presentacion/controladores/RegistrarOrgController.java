package presentacion.controladores;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import repositorios.RepositorioUsuarios;
import domain.ubicaciones.Ubicacion;
import presentacion.Usuario;
import presentacion.errores.Error;
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
    String nombre           = request.queryParams("nombre");
    String razonSocial      = request.queryParams("razonSocial");
    String tipo             = request.queryParams("tipo");
    String clasificacion    = request.queryParams("clasificacion");
    String pais             = request.queryParams("ubicacionPais");
    String provincia        = request.queryParams("ubicacionProvincia");
    String municipio        = request.queryParams("ubicacionMunicipio");
    String localidad        = request.queryParams("ubicacionLocalidad");
    String calle            = request.queryParams("ubicacionCalle");
    int altura              = Integer.parseInt(request.queryParams("ubicacionAltura"));

    Ubicacion ubicacion = new Ubicacion(calle, altura,
        pais, provincia, municipio, localidad);

    // Validacion de Ubicacion
    Error error = new Error();
    try {
      ubicacion.getLocalidad();
    } catch (Exception e) {
      error.setError(true);
      error.setDescripcion(e.getMessage() + " (en los datos ingresados correspondientes a la " +
          "ubicacion) ");
      e.printStackTrace();
      return new ModelAndView(error, "registrarOrg.hbs");
    }

    Organizacion org = new Organizacion(nombre, razonSocial, TipoOrganizacion.valueOf(tipo),
        ubicacion, ClasificacionOrg.valueOf(clasificacion));

    Usuario usuario = request.session().attribute("usuario_signeado_literal");
    usuario.setOrg(org);
    RepositorioUsuarios.getInstance().update(usuario);

    request.session().attribute("usuario_logueado", usuario.getUsername());
    response.redirect("/home");
    return null;
  }

}
