package presentacion.controladores;

import domain.organizaciones.miembros.Miembro;
import domain.repositorios.RepositorioMiembros;
import domain.repositorios.RepositorioTransportes;
import domain.repositorios.RepositorioUsuarios;
import domain.servicios.geodds.ServicioGeoDds;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.TrayectoCompartido;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.trayecto.transporte.nopublico.*;
import domain.ubicaciones.Ubicacion;
import presentacion.Usuario;
import presentacion.errores.Error;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    List<MedioDeTransporte> transportes = RepositorioTransportes.getInstance().all();
    Map<String, Object> model = new HashMap<>();
    model.put("transportes", transportes);
    return new ModelAndView(transportes, "tramo.hbs");
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

    String medio = request.queryParams("medio");

    Tramo tramo = null;
    switch (medio){
      case "pie":
        tramo = new Tramo( new Pie(), ubicacionInicial, ubicacionFin);
        break;
      case "bici":
        tramo = new Tramo( new Bicicleta(), ubicacionInicial, ubicacionFin);
        break;
      case "servicioContratado":
        String tipo = request.queryParams("tipoDeServicio");
        String combustibleConsumidoServicio = request.queryParams("combustibleConsumidoPorKM");
        ServicioContratado servicioContratado = new ServicioContratado(new TipoServicioContratado(tipo), Double.parseDouble(combustibleConsumidoServicio));
        tramo = new Tramo(servicioContratado, ubicacionInicial, ubicacionFin);
        break;
      case "vehiculoParticular":
        String tipoVehiculo = request.queryParams("tipoDeVehiculo");
        String tipoCombustible = request.queryParams("tipoDeCombustible");
        String combustibleConsumidoVehiculo = request.queryParams("combustibleConsumidoPorKM");
        VehiculoParticular vehiculoParticular = new VehiculoParticular(TipoDeVehiculo.valueOf(tipoVehiculo), TipoDeCombustible.valueOf(tipoCombustible), Double.parseDouble(combustibleConsumidoVehiculo));
        tramo = new Tramo(vehiculoParticular, ubicacionInicial, ubicacionFin);
        break;
      default:
        break;
    }

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
