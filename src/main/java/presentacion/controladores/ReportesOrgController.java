package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.organizaciones.datos.actividades.tipos.DataHelper;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import domain.organizaciones.hc.HC;
import presentacion.Usuario;
import repositorios.RepositorioConsumos;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReportesOrgController {

  public ModelAndView index(Request request, Response response) {
    DecimalFormat df = new DecimalFormat("0.00");

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

    HC total = org.hcTotal();

    List<Double> valoresHistorial = new ArrayList<>();
    org.getHistorialHCTotal().forEach(hcT -> valoresHistorial.add(hcT.enKgCO2()));

    Map<String, Object> model = new HashMap<>();
    //model.put("datos",datos);

    String nombreOrg = org.getNombreOrg();

    model.put("organizacion", nombreOrg);
    model.put("nombres", nombres);
    model.put("porcentajes", porcentajes);
    model.put("total", df.format(total.enKgCO2()));
    model.put("valoresHistorial", valoresHistorial);
    return new ModelAndView(model, "reportesOrg.hbs");
  }
}
