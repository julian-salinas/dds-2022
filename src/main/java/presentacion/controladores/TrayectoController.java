package presentacion.controladores;

import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.repositorios.RepositorioMiembros;
import domain.repositorios.RepositorioUsuarios;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.TrayectoCompartido;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.trayecto.transporte.nopublico.Bicicleta;
import domain.ubicaciones.Ubicacion;
import presentacion.TipoUsuario;
import presentacion.Usuario;
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


    Ubicacion ubicacionInicial  = new Ubicacion(request.queryParams("calle"), Integer.parseInt(request.queryParams("altura")), request.queryParams("localidad"));
    Ubicacion ubicacionFin  = new Ubicacion(request.queryParams("calle2"), Integer.parseInt(request.queryParams("altura2")), request.queryParams("localidad2"));

    Tramo tramo = new Tramo( new Bicicleta(), ubicacionInicial, ubicacionFin);

    trayecto.agregarTramo(tramo);


    String username = request.cookie("username");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);

    Miembro miembro = user.getMiembro();

    miembro.registrarTrayecto(trayecto);

    RepositorioMiembros.getInstance().update(miembro);

    return new ModelAndView(null, "trayecto.hbs");
  }


}
