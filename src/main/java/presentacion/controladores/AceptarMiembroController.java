package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.sectores.Sector;
import repositorios.RepositorioMiembros;
import repositorios.RepositorioOrganizaciones;
import repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.stream.Collectors;

public class AceptarMiembroController {

  public ModelAndView index(Request request, Response response) {
    String username = request.session().attribute("usuario_logueado");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();
    return new ModelAndView(org, "aceptarMiembros.hbs");
  }

  public ModelAndView post(Request request, Response response) {
    String username = request.session().attribute("usuario_logueado");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();

    int miembroid = Integer.parseInt(request.queryParams("id"));
    Miembro miembro = RepositorioMiembros.getInstance().get(miembroid);

    Sector sector = org
        .getSectores()
        .stream()
        .filter(sec -> sec.containsMiembroParaAceptar(miembro))
        .collect(Collectors.toList())
        .get(0);

    org.aceptarVinculacionDeTrabajador(miembro, sector);
    RepositorioOrganizaciones.getInstance().update(org);

    response.redirect("/home");
    return null;
  }

}
