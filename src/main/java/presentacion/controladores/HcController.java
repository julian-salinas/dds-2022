package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.organizaciones.hc.HC;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class HcController {

  public ModelAndView index(Request request, Response response) {

    /*String username = request.cookie("username"); --> por las dudas, comento q esto paso a session
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

    // Para mostrar despues 2 decimales
    DecimalFormat df = new DecimalFormat("0.000");

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
      model.put("nombre", "nombre"); // para q funque lo de los botones de signin y login
      return new ModelAndView(model, "hc.hbs");
    }

    String mes = request.queryParams("mes");
    String anio = request.queryParams("anio");

    if(tipoCalculo.equals("Mensual")) {
      HC hcMensual = organizacion.hcMensual(mes);

      if (unidadHC.equals("gCO2")) {
        model.put("mensual", df.format(hcMensual.enGCO2()));
      }
      else if (unidadHC.equals("kgCO2")) {
        model.put("mensual", df.format(hcMensual.enKgCO2()));
      }
      else {// if(unidadHC.equals("tnCO2"))
        model.put("mensual", df.format(hcMensual.enTnCO2()));
      }
    } else { //if(tipoCalculo.equals("Anual"))
      HC hcAnual = organizacion.hcAnual(anio);

      if (unidadHC.equals("gCO2")) {
        model.put("anual", df.format(hcAnual.enGCO2()));
      }
      else if (unidadHC.equals("kgCO2")) {
        model.put("anual", df.format(hcAnual.enKgCO2()));
      }
      else {// if(unidadHC.equals("tnCO2"))
        model.put("anual", df.format(hcAnual.enTnCO2()));
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

    // Para mostrar despues 2 decimales
    DecimalFormat df = new DecimalFormat("0.000");

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
      model.put("nombre", "nombre"); // para q funque lo de los botones de signin y login
      return new ModelAndView(model, "hcMiembro.hbs");
    }

    if (unidadHC.equals("gCO2")) {
      model.put("mensual", df.format(hcMiembro.enGCO2()));
    }
    else if (unidadHC.equals("kgCO2")) {
      model.put("mensual", df.format(hcMiembro.enKgCO2()));
    }
    else {// if(unidadHC.equals("tnCO2"))
      model.put("mensual", df.format(hcMiembro.enTnCO2()));
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

    // Para mostrar despues 2 decimales
    DecimalFormat df = new DecimalFormat("0.000");

    String username = request.session().attribute("usuario_logueado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    AgenteSectorial agenteSectorial = user.getAgenteSectorial();
    String unidadHC = request.queryParams("unidadHC");
    Map<String, Object> model = new HashMap<>();

    HC hcAgente;
    Error error = new Error();
    error.setError(false);

    // Validar q no haya Timeout
    try {
      hcAgente= agenteSectorial.hcSectorMensual("2020"); // string prueba
    } catch (TimeoutException e) {
      e.printStackTrace();
      error.setError(true);
      error.setDescripcion("Timeout, por favor intente otra vez.");
      model.put("error", error);
      model.put("nombre", "nombre"); // para q funque lo de los botones de signin y login
      return new ModelAndView(model, "hcAgente.hbs");
    }

    if (unidadHC.equals("gCO2")) {
      model.put("mensual", df.format(hcAgente.enGCO2()));
    }
    else if (unidadHC.equals("kgCO2")) {
      model.put("mensual", df.format(hcAgente.enKgCO2()));
    }
    else {// if(unidadHC.equals("tnCO2"))
      model.put("mensual", df.format(hcAgente.enTnCO2()));
    }

    model.put("unidad", unidadHC);

    // Para lo de los botones de log in y sign in
    model.put("nombre", username);

    return new ModelAndView(model, "hcAgente.hbs");
  }

}
