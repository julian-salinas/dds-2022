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

    Trayecto trayecto = new Trayecto();
    request.session().attribute("trayecto", trayecto);

    return new ModelAndView(null, "tramo.hbs");
  }

  public ModelAndView postCompartido(Request request, Response response) {

    Trayecto trayecto = new TrayectoCompartido();
    request.session().attribute("trayecto", trayecto);

    return new ModelAndView(null, "tramo.hbs");
  }

  public ModelAndView agregarTramo(Request request, Response response) {

    Trayecto trayecto = request.session().attribute("trayecto");


    Ubicacion ubicacionInicial  = new Ubicacion(request.queryParams("calle"), Integer.parseInt(request.queryParams("altura")), request.queryParams("localidad"));
    Ubicacion ubicacionFin  = new Ubicacion(request.queryParams("calle2"), Integer.parseInt(request.queryParams("altura2")), request.queryParams("localidad2"));

    System.out.print("Hola hola");
    Tramo tramo = new Tramo( new Bicicleta(), ubicacionInicial, ubicacionFin);

    trayecto.agregarTramo(tramo);

    System.out.print("chau chau");

    String username = request.session().attribute("usuario-logeado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);




    Miembro miembro = new Miembro("Juan", "Lopez", TipoDeDocumento.DNI, 4444);
    RepositorioMiembros.getInstance().add(miembro);

    System.out.print("Agregue a Juan");
    miembro.agregarTrayecto(trayecto);

    return new ModelAndView(null, "trayecto.hbs");
  }


}
