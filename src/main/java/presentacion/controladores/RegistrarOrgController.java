package presentacion.controladores;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.ubicaciones.Ubicacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RegistrarOrgController {

  public ModelAndView index(Request request, Response response) {
    return new ModelAndView(null, "registrarOrg.hbs");
  }

  public ModelAndView post(Request request, Response response) {
    // TODO
    String nombre = request.queryParams("nombre");
    String razonSocial = request.queryParams("razonSocial");
    String tipo = request.queryParams("tipo");
    String clasificacion = request.queryParams("clasificacion");
    String ubicacion = request.queryParams("ubicacion");
    String sector = request.queryParams("sector");

    Organizacion org = new Organizacion(nombre, razonSocial, TipoOrganizacion.valueOf(tipo),
        new Ubicacion(), ClasificacionOrg.valueOf(clasificacion));

    return new ModelAndView(org, "organizacion.hbs");
  }

}
