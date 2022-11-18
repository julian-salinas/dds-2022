package presentacion.controladores;

import domain.trayecto.transporte.publico.Linea;
import domain.trayecto.transporte.publico.Parada;
import domain.trayecto.transporte.publico.TipoTransportePublico;
import domain.trayecto.transporte.publico.TransportePublico;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.distancia.UnidadDistancia;
import repositorios.RepositorioLineas;
import repositorios.RepositorioTransportes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.*;
import java.util.stream.Collectors;

public class CargarLineaController {

  public ModelAndView index(Request request, Response response) {
    // Checkear si es admin
//    String username = request.session().attribute("usuario_logueado");
//    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
//    if(user.getTipo() != TipoUsuario.ADMINISTRADOR) {
//      response.redirect("/home");
//    }

    Set<TipoTransportePublico> tipos = EnumSet.allOf(TipoTransportePublico.class);
    List<String> nombreTipos = tipos.stream().map(t -> t.toString()).collect(Collectors.toList());

    Map<String, Object> model = new HashMap<>();
    model.put("nombreTipos", nombreTipos);

    return new ModelAndView(model, "cargarLinea.hbs");
  }

  public ModelAndView post(Request request, Response response){
    String tipo = request.queryParams("tipo");
    String nombre = request.queryParams("nombre");
    String bidireccional_s = request.queryParams("bidireccional");
    boolean bidireccional = bidireccional_s.equals("Si");

    TipoTransportePublico tipoTransporte = TipoTransportePublico.valueOf(tipo);

    Linea linea = new Linea(nombre);
    if(bidireccional)
      linea.setBidireccional();
    else
      linea.setUnidireccional();

    //RepositorioLineas.getInstance().add(linea);
    TransportePublico transportePublico = new TransportePublico(tipoTransporte, linea);
    RepositorioTransportes.getInstance().add(transportePublico);

    response.redirect("/home"); // si manda a login es xq no me logie dsp de ejecutar.
    return null;
  }
}
