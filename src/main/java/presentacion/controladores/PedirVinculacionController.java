package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.sectores.Sector;
import domain.repositorios.RepositorioMiembros;
import domain.repositorios.RepositorioOrganizaciones;
import domain.repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PedirVinculacionController {

  public ModelAndView index(Request request, Response response) {
//    String username = request.cookie("username");
//    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    List<Organizacion> organizaciones = RepositorioOrganizaciones.getInstance().all();
    Map<String, Object> model = new HashMap<>();
    model.put("organizaciones", organizaciones);
    return new ModelAndView(model, "vincularAOrg.hbs");
  }

  public ModelAndView postOrg(Request request, Response response) {
    int id = Integer.parseInt(request.queryParams("id"));
    Organizacion organizacion = RepositorioOrganizaciones.getInstance().get(id);
    Map<String, Object> model = new HashMap<>();
    model.put("sectores", organizacion.getSectores());
    request.session().attribute("org-id", id);
    return new ModelAndView(model, "vincularASector.hbs");
  }

  public ModelAndView mandar(Request request, Response response) {
    String username = request.cookie("username");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Miembro miembro = usuario.getMiembro();

    int orgid = request.session().attribute("org-id");
    int sectorid = Integer.parseInt(request.queryParams("id"));


    Organizacion organizacion = RepositorioOrganizaciones.getInstance().get(orgid);
    Sector sector = organizacion
        .getSectores()
        .stream()
        .filter(sec -> sec.getId()==sectorid)
        .collect(Collectors.toList())
        .get(0);

    miembro.vincularTrabajadorConOrg(organizacion, sector);
    RepositorioMiembros.getInstance().update(miembro);
    RepositorioOrganizaciones.getInstance().update(organizacion);

    response.redirect("/home");
    return null;
  }

}
