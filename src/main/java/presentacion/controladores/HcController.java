package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.repositorios.RepositorioOrganizaciones;
import domain.repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HcController {

  public ModelAndView index(Request request, Response response) {

    String username = request.cookie("username");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);

    Organizacion organizacion = user.getOrg();

    Map<String, Object> model = new HashMap<>();
    model.put("esMensual", true);
    /*model.put("mensual", organizacion.hcMensual().enKgCO2());
    model.put("anual", organizacion.hcAnual().enKgCO2());

    RepositorioOrganizaciones.getInstance().update(organizacion);*/

    return new ModelAndView(model, "hc.hbs");
  }

  public ModelAndView post(Request request, Response response) {

    String username = request.cookie("username");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion organizacion = user.getOrg();
    String tipoCalculo = request.queryParams("tipoCalculo");
    String unidadHC    = request.queryParams("unidadHC");
    Map<String, Object> model = new HashMap<>();

    if(tipoCalculo.equals("Mensual")) {
      if (unidadHC.equals("gCO2")) {
        model.put("mensual", organizacion.hcMensual().enGCO2());
      }
      else if (unidadHC.equals("kgCO2")) {
        model.put("mensual", organizacion.hcMensual().enKgCO2());
      }
      else {// if(unidadHC.equals("tnCO2"))
        model.put("mensual", organizacion.hcMensual().enTnCO2());
      }
    } else { //if(tipoCalculo.equals("Anual"))
      if (unidadHC.equals("gCO2")) {
        model.put("anual", organizacion.hcAnual().enGCO2());
      }
      else if (unidadHC.equals("kgCO2")) {
        model.put("anual", organizacion.hcAnual().enKgCO2());
      }
      else {// if(unidadHC.equals("tnCO2"))
        model.put("anual", organizacion.hcAnual().enTnCO2());
      }
    }
    if(tipoCalculo.equals("Mensual"))
      model.put("esMensual", true);
    else
      model.put("esMensual", false);

    model.put("unidad", unidadHC);

    RepositorioOrganizaciones.getInstance().update(organizacion);

    return new ModelAndView(model, "hc.hbs");
  }

}
