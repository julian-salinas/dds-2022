package presentacion.controladores;

import domain.servicios.geodds.ServicioGeoDds;
import domain.servicios.geodds.entidades.Provincia;
import domain.ubicaciones.sectores.AgenteSectorial;
import domain.ubicaciones.sectores.TipoSectorTerritorial;
import presentacion.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarAgSecController {

  public ModelAndView redirect(Request request, Response response) {
    response.redirect("/registrarAgSec/tipo");
    return null;
  }

  public ModelAndView index(Request request, Response response) {
    String username = request.session().attribute("usuario_signeado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    request.session().attribute("usuario_signeado_literal", user);
    return new ModelAndView(null, "elegirSectorTerritorial.hbs");
  }

  //TODO
  public ModelAndView post(Request request, Response response) {
    TipoSectorTerritorial tipo = TipoSectorTerritorial.valueOf(request.queryParams("tipo"));
    request.session().attribute("tipo_sec_terr", tipo);
    response.redirect("/registrarAgSec/sector");
    return null;
  }

  public ModelAndView index_sector(Request request, Response response) {
    TipoSectorTerritorial tipo = request.session().attribute("tipo_sec_terr");

    Map<String, Object> model = new HashMap<>();
    model.put("tipoSectorTerritorial", tipo);


    if(tipo.equals(TipoSectorTerritorial.MUNICIPIO)) {
      List<Provincia> provincias = ServicioGeoDds.getInstancia().allProvincias();
      model.put("provincias", provincias);
    } else {
      List<Provincia> provincias = ServicioGeoDds.getInstancia().allProvincias();
      model.put("provincias", provincias);
    }

    return new ModelAndView(model, "elegirSectorTerritorial.hbs");
  }

  //TODO
  public ModelAndView post_sector(Request request, Response response) {
    TipoSectorTerritorial tipo = TipoSectorTerritorial.valueOf(request.queryParams("tipo"));

    AgenteSectorial agenteSectorial = new AgenteSectorial(tipo, 335);
    return new ModelAndView(agenteSectorial, "elegirSectorTerritorial.hbs");
  }

}
