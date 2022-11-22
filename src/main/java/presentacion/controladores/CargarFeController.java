package presentacion.controladores;

import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.datos.actividades.tipos.NoCoincidenUnidadesFEYTC;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import domain.trayecto.transporte.publico.Linea;
import domain.trayecto.transporte.publico.TipoTransportePublico;
import domain.trayecto.transporte.publico.TransportePublico;
import org.codehaus.stax2.ri.typed.ValueDecoderFactory;
import presentacion.errores.Error;
import repositorios.RepositorioConsumos;
import repositorios.RepositorioTransportes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.*;
import java.util.stream.Collectors;

public class CargarFeController {

  public ModelAndView index(Request request, Response response) {
    // Checkear si es admin
//    String username = request.session().attribute("usuario_logueado");
//    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
//    if(user.getTipo() != TipoUsuario.ADMINISTRADOR) {
//      response.redirect("/home");
//    }
    List<TipoDeConsumo> consumos = RepositorioConsumos.getInstance().all();

    Set<UnidadConsumo> tipos = EnumSet.allOf(UnidadConsumo.class);
    List<String> nombreTipos = tipos.stream().map(t -> t.toString()).collect(Collectors.toList());

    Map<String, Object> model = new HashMap<>();
    model.put("nombreTipos", nombreTipos);
    model.put("consumos", consumos);
    if(request.session().attribute("error") != null) {
      Error error = new Error();
      error.setError(true);
      error.setDescripcion("No coincide la unidad.");
      model.put("error", error);
      request.session().attribute("error", null);
    }

    return new ModelAndView(model, "cargarFe.hbs");
  }

  public ModelAndView post(Request request, Response response){
    String tipo = request.queryParams("tipo");
    double valor = Double.parseDouble(request.queryParams("valor"));
    int consumo = Integer.parseInt(request.queryParams("consumo"));

    UnidadConsumo unidad = UnidadConsumo.valueOf(tipo);
    FactorEmision factor = new FactorEmision(valor, unidad);
    TipoDeConsumo tipoConsumo = RepositorioConsumos.getInstance().findByID(consumo);

    // Validacion de unidades
    try {
      tipoConsumo.cargarFactorEmision(factor);
    } catch (NoCoincidenUnidadesFEYTC e) {
      /*error.setError(true);
      error.setDescripcion("");
      return new ModelAndView(error, "cargarFe.hbs");*/
      request.session().attribute("error", true);
      response.redirect("/cargarFe");
    }

    response.redirect("/home"); // si manda a login es xq no me logie dsp de ejecutar.
    return null;
  }
}
