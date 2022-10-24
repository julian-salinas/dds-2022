package presentacion.controladores;

import domain.organizaciones.miembros.Miembro;
import domain.repositorios.RepositorioMiembros;
import domain.repositorios.RepositorioUsuarios;
import domain.servicios.geodds.ServicioGeoDds;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.TrayectoCompartido;
import domain.trayecto.transporte.nopublico.Pie;
import domain.ubicaciones.Ubicacion;
import presentacion.Usuario;
import presentacion.errores.Error;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class TrayectoController {

  public ModelAndView index(Request request, Response response) {
    return new ModelAndView(null, "trayecto.hbs");
  }

  public ModelAndView post(Request request, Response response) {
    String tipo = request.queryParams("trayecto");
    Trayecto trayecto;
    if(tipo.equals("trayecto"))
      trayecto = new Trayecto();
    else if (tipo.equals("trayecto-comp"))
      trayecto = new TrayectoCompartido();
    else
      return null;
    request.session().attribute("trayecto", trayecto);

    return new ModelAndView(null, "tramo.hbs");
  }

  public ModelAndView agregarTramo(Request request, Response response) {

    Trayecto trayecto = request.session().attribute("trayecto");

    String paisInicio = request.queryParams("paisInicio");
    String provinciaInicio = request.queryParams("provinciaInicio");
    String municipioInicio = request.queryParams("municipioInicio");
    String localidadInicio = request.queryParams("localidadInicio");
    String calleInicio = request.queryParams("calleInicio");
    int alturaInicio = Integer.parseInt(request.queryParams("alturaInicio"));

    String paisFin = request.queryParams("paisFin");
    String provinciaFin = request.queryParams("provinciaFin");
    String municipioFin = request.queryParams("municipioFin");
    String localidadFin = request.queryParams("localidadFin");
    String calleFin = request.queryParams("calleFin");
    int alturaFin = Integer.parseInt(request.queryParams("alturaFin"));

    String boton = request.queryParams("tramo");

    Ubicacion ubicacionInicial  = new Ubicacion(calleInicio, alturaInicio, paisInicio,
        provinciaInicio, municipioInicio, localidadInicio);

    Ubicacion ubicacionFin  = new Ubicacion(calleFin, alturaFin, paisFin,
        provinciaFin, municipioFin, localidadFin);


    // Validacion de Ubicaciones
    Error error = new Error();
    String cualUbicacion = "Ubicacion Inicio";
    try {
      ubicacionInicial.getLocalidad();
      cualUbicacion = "Ubicacion Fin";
      ubicacionFin.getLocalidad();
    } catch (Exception e) {
      error.setError(true);
      error.setDescripcion(e.getMessage() + " en " + cualUbicacion);
      e.printStackTrace();
      return new ModelAndView(error, "tramo.hbs");
    }


    Tramo tramo = new Tramo( new Pie(), ubicacionInicial, ubicacionFin);

    trayecto.agregarTramo(tramo);

    if(boton.equals("fin")) {

      String username = request.cookie("username");
      Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);

      Miembro miembro = user.getMiembro();

      miembro.registrarTrayecto(trayecto);

      RepositorioMiembros.getInstance().update(miembro);


      return new ModelAndView(null, "trayecto.hbs");
    }
    else return new ModelAndView(null, "tramo.hbs");
  }


}
