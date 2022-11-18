package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.organizaciones.datos.actividades.tipos.DataHelper;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import presentacion.Usuario;
import repositorios.RepositorioConsumos;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReportesOrgController {

  public ModelAndView index(Request request, Response response) {

    List<TipoDeConsumo> consumos = RepositorioConsumos.getInstance().all();
    String username = request.session().attribute("usuario_logueado");

    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = user.getOrg();

    List<Double> porcentajes = new ArrayList<>();

    //List<Integer> porcentajes = consumos.stream().map(c -> c.getId()).collect(Collectors.toList());
    List<String> nombres = consumos.stream().map(c -> c.getTipo()).collect(Collectors.toList());

    nombres.forEach(tipo -> {
      double porcentajeTipo = org.composicionHCMensual(tipo);
      porcentajes.add(porcentajeTipo);
    });

    List<DataHelper> datos = IntStream.range(0, nombres.size())
        .mapToObj(i -> new DataHelper((i < porcentajes.size() ? porcentajes.get(i): null),
            (i < nombres.size() ? nombres.get(i): null)))
        .collect(Collectors.toList());



    Map<String, Object> model = new HashMap<>();
    model.put("datos",datos);
    return new ModelAndView(model, "reportesOrg.hbs");
  }
}
