package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.organizaciones.hc.HC;
import domain.organizaciones.hc.UnidadHC;
import domain.organizaciones.miembros.Miembro;
import domain.servicios.geodds.excepciones.TimeoutException;
import domain.ubicaciones.sectores.AgenteSectorial;
import presentacion.errores.Error;
import repositorios.RepositorioMiembros;
import repositorios.RepositorioOrganizaciones;
import repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class HcController {

  public ModelAndView index(Request request, Response response) {

    /*String username = request.cookie("username"); --> por las dudas, comento q esto paso a seesion
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);

    Organizacion organizacion = user.getOrg();*/

    Map<String, Object> model = new HashMap<>();
    model.put("esMensual", true);
    /*model.put("mensual", organizacion.hcMensual().enKgCO2());
    model.put("anual", organizacion.hcAnual().enKgCO2());

    RepositorioOrganizaciones.getInstance().update(organizacion);*/

    return new ModelAndView(model, "hc.hbs");
  }

  public ModelAndView post(Request request, Response response) {

    String username = request.session().attribute("usuario_logueado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion organizacion = user.getOrg();
    String tipoCalculo = request.queryParams("tipoCalculo");
    String unidadHC    = request.queryParams("unidadHC");
    Map<String, Object> model = new HashMap<>();

    Error error = new Error();
    error.setError(false);

    // Validar q no haya Timeout
    try {
      organizacion.cargarDATransladoMiembros();
    } catch (TimeoutException e) {
      e.printStackTrace();
      error.setError(true);
      error.setDescripcion("Timeout, por favor intente otra vez.");
      model.put("error", error);
      return new ModelAndView(model, "hc.hbs");
    }

    HC hcMensual = organizacion.hcMensual();
    HC hcAnual = organizacion.hcAnual();

    if(tipoCalculo.equals("Mensual")) {
      if (unidadHC.equals("gCO2")) {
        model.put("mensual", hcMensual.enGCO2());
      }
      else if (unidadHC.equals("kgCO2")) {
        model.put("mensual", hcMensual.enKgCO2());
      }
      else {// if(unidadHC.equals("tnCO2"))
        model.put("mensual", hcMensual.enTnCO2());
      }
    } else { //if(tipoCalculo.equals("Anual"))
      if (unidadHC.equals("gCO2")) {
        model.put("anual", hcAnual.enGCO2());
      }
      else if (unidadHC.equals("kgCO2")) {
        model.put("anual", hcAnual.enKgCO2());
      }
      else {// if(unidadHC.equals("tnCO2"))
        model.put("anual", hcAnual.enTnCO2());
      }
    }
    if(tipoCalculo.equals("Mensual"))
      model.put("esMensual", true);
    else
      model.put("esMensual", false);

    model.put("unidad", unidadHC);

    // Para lo de los botones de log in y sign in
    model.put("nombre", username);

    RepositorioOrganizaciones.getInstance().update(organizacion);

    return new ModelAndView(model, "hc.hbs");
  }

  public ModelAndView indexMiembro(Request request, Response response) {
    String username = request.session().attribute("usuario_logueado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    Object model = user.getMiembro();
    return new ModelAndView(model, "hcMiembro.hbs");
  }

  public ModelAndView postMiembro(Request request, Response response) {

    String username = request.session().attribute("usuario_logueado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    Miembro miembro = user.getMiembro();
    String unidadHC = request.queryParams("unidadHC");
    Map<String, Object> model = new HashMap<>();

    HC hcMiembro;
    Error error = new Error();
    error.setError(false);

    // Validar q no haya Timeout
    try {
      hcMiembro = miembro.calculoHCPersonal();
    } catch (TimeoutException e) {
      e.printStackTrace();
      error.setError(true);
      error.setDescripcion("Timeout, por favor intente otra vez.");
      model.put("error", error);
      return new ModelAndView(model, "hcMiembro.hbs");
    }

    if (unidadHC.equals("gCO2")) {
      model.put("mensual", hcMiembro.enGCO2());
    }
    else if (unidadHC.equals("kgCO2")) {
      model.put("mensual", hcMiembro.enKgCO2());
    }
    else {// if(unidadHC.equals("tnCO2"))
      model.put("mensual", hcMiembro.enTnCO2());
    }

    model.put("unidad", unidadHC);

    // Para lo de los botones de log in y sign in
    model.put("nombre", username);

    //RepositorioMiembros.getInstance().update(miembro);

    return new ModelAndView(model, "hcMiembro.hbs");
  }

  public ModelAndView index_agente(Request request, Response response) {
    //Para lo de los botones de log in y sign in
    Map<String, Object> model = new HashMap<>();
    model.put("nombre", "nombre");
    return new ModelAndView(model, "hcAgente.hbs");
  }

  public ModelAndView post_agente(Request request, Response response) {

    String username = request.session().attribute("usuario_logueado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    AgenteSectorial agenteSectorial = user.getAgenteSectorial();
    String unidadHC    = request.queryParams("unidadHC");
    Map<String, Object> model = new HashMap<>();

    HC hcAgente;
    Error error = new Error();
    error.setError(false);

    // Validar q no haya Timeout
    try {
      hcAgente= agenteSectorial.hcSectorMensual();
    } catch (TimeoutException e) {
      e.printStackTrace();
      error.setError(true);
      error.setDescripcion("Timeout, por favor intente otra vez.");
      model.put("error", error);
      return new ModelAndView(model, "hcAgente.hbs");
    }

    if (unidadHC.equals("gCO2")) {
      model.put("mensual", hcAgente.enGCO2());
    }
    else if (unidadHC.equals("kgCO2")) {
      model.put("mensual", hcAgente.enKgCO2());
    }
    else {// if(unidadHC.equals("tnCO2"))
      model.put("mensual", hcAgente.enTnCO2());
    }

    model.put("unidad", unidadHC);

    // Para lo de los botones de log in y sign in
    model.put("nombre", username);

    return new ModelAndView(model, "hcAgente.hbs");
  }

}
