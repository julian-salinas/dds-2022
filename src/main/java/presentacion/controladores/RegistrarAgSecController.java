package presentacion.controladores;

import domain.servicios.geodds.ServicioGeoDds;
import domain.servicios.geodds.entidades.Municipio;
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
      List<Municipio> municipios = ServicioGeoDds.getInstancia().allMunicipios();
      model.put("municipios", municipios);
    } else {
      List<Provincia> provincias = ServicioGeoDds.getInstancia().allProvincias();
      model.put("provincias", provincias);
    }

    return new ModelAndView(model, "elegirSectorTerritorial.hbs");
  }

  public ModelAndView post_sector(Request request, Response response) {
    TipoSectorTerritorial tipo = TipoSectorTerritorial.valueOf(request.queryParams("tipo"));
    int id = Integer.parseInt(request.queryParams("idSector"));
    Usuario usuario = request.session().attribute("usuario_signeado_literal");

    AgenteSectorial agenteSectorial = new AgenteSectorial(tipo, id);
    agenteSectorial.setNombreAgente("Panamiguel");
    usuario.setAgenteSectorial(agenteSectorial);

    RepositorioUsuarios.getInstance().update(usuario);

    request.session().attribute("usuario_logueado", usuario.getUsername());
    response.redirect("/home");
    return null;
  }

}
