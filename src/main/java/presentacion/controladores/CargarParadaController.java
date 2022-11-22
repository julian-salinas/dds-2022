package presentacion.controladores;

import domain.trayecto.transporte.publico.Linea;
import domain.trayecto.transporte.publico.Parada;
import domain.trayecto.transporte.publico.TransportePublico;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.distancia.UnidadDistancia;
import repositorios.RepositorioLineas;
import repositorios.RepositorioTransportes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CargarParadaController {

  public ModelAndView index(Request request, Response response) {
    // Checkear si es admin
//    String username = request.session().attribute("usuario_logueado");
//    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
//    if(user.getTipo() != TipoUsuario.ADMINISTRADOR) {
//      response.redirect("/home");
//    }

    List<Linea> lineas = RepositorioLineas.getInstance().all();
    Map<String, Object> model = new HashMap<>();
    model.put("lineas", lineas);

    return new ModelAndView(model, "cargarParada.hbs");
  }

  public ModelAndView post(Request request, Response response){
    int id = Integer.parseInt(request.queryParams("idLinea"));
    String nombre = request.queryParams("nombre");
    int distancia = Integer.parseInt(request.queryParams("distancia"));
    String calle = request.queryParams("calle");
    int altura = Integer.parseInt(request.queryParams("altura"));
    String pais = request.queryParams("pais");
    String provincia = request.queryParams("provincia");
    String municipio = request.queryParams("municipio");
    String localidad = request.queryParams("localidad");


    Ubicacion ubicacion = new Ubicacion(calle, altura, pais, provincia, municipio, localidad);
    Distancia distanciaParada = new Distancia(distancia, UnidadDistancia.MTS);

    Linea linea = RepositorioTransportes.getInstance().findByLinea(id).getLinea();
    linea.agregarParada(new Parada(nombre, ubicacion, distanciaParada));

    response.redirect("/home"); // si manda a login es xq no me logie dsp de ejecutar.
    return null;
  }
}
