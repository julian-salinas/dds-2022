package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.organizaciones.sectores.Sector;
import domain.repositorios.RepositorioOrganizaciones;
import domain.repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class SectoresController {

  public ModelAndView index(Request request, Response response) {

    String username = request.cookie("username");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);

    Organizacion organizacion = user.getOrg();

//    Map<String, Object> model = new HashMap<>();
//
//    model.put("sectores", organizacion.getSectores());

    return new ModelAndView(organizacion, "sectores.hbs");
  }

  public ModelAndView post(Request request, Response response) {
    String nombre       = request.queryParams("nombre");
    String descripcion  = request.queryParams("descripcion");
    String username = request.cookie("username");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);

    Organizacion organizacion = user.getOrg();

    Sector sector = new Sector(nombre, descripcion);

    organizacion.agregarSector(sector);

    RepositorioOrganizaciones.getInstance().update(organizacion);

    return new ModelAndView(organizacion, "sectores.hbs");
  }

}
