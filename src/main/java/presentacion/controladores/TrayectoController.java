package presentacion.controladores;

import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.repositorios.RepositorioMiembros;
import domain.repositorios.RepositorioUsuarios;
import domain.servicios.geodds.ServicioGeoDds;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.TrayectoCompartido;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.trayecto.transporte.nopublico.Bicicleta;
import domain.trayecto.transporte.nopublico.Pie;
import domain.ubicaciones.Ubicacion;
import presentacion.TipoUsuario;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;

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

    String calleInicio = request.queryParams("calle");
    int alturaInicio = Integer.parseInt(request.queryParams("altura"));
    String localidadInicio = request.queryParams("localidad");
    String calleFin = request.queryParams("calle2");
    int alturaFin = Integer.parseInt(request.queryParams("altura2"));
    String localidadFin = request.queryParams("localidad2");

    String boton = request.queryParams("tramo");

    ServicioGeoDds api = ServicioGeoDds.getInstancia();

    try {
      int idLocalidad = api.verificarNombreLocalidad(localidadInicio);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Ubicacion ubicacionInicial  = new Ubicacion(calleInicio, alturaInicio, localidadInicio);

    Ubicacion ubicacionFin  = new Ubicacion(calleFin, alturaFin, localidadFin);

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
