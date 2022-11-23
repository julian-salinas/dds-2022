package presentacion.controladores;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.datos.actividades.tipos.DataHelper;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import domain.organizaciones.hc.HC;
import domain.ubicaciones.sectores.AgenteSectorial;
import presentacion.Usuario;
import repositorios.RepositorioConsumos;
import repositorios.RepositorioOrganizaciones;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReportesAgSecController {

  public ModelAndView index(Request request, Response response) {
    DecimalFormat df = new DecimalFormat("0.00");
    String username = request.session().attribute("usuario_logueado");
    Usuario user = RepositorioUsuarios.getInstance().findByUsername(username);

    AgenteSectorial agente = user.getAgenteSectorial();

    // Consumos
    List<TipoDeConsumo> consumos = RepositorioConsumos.getInstance().all();
    List<String> nombres = consumos.stream().map(TipoDeConsumo::getTipo).collect(Collectors.toList());

    // Valor que aporta al hc total cada tipo de consumo
    List<Double> porcentajes = new ArrayList<>();
    nombres.forEach(tipo -> {
      List<Organizacion> organizaciones = agente.encontrarOrgs();
      double porcentajeTipo = organizaciones.stream().mapToDouble(organizacion -> organizacion.composicionHCTotal(tipo)).sum();
      porcentajes.add(porcentajeTipo);
    });

    // Hc total
    HC total = agente.hcTotal();

    // Historial de HC Para grafico de barras
    List<Double> valoresHistorial = new ArrayList<>();
    agente.getHistorialHCTotal().forEach(hcT -> valoresHistorial.add(hcT.enKgCO2()));

    // Nombre y ubicacion de agente
    String nombreAgente = agente.getNombreAgente();
    String ubicacion = agente.getNombreSectorTerritorial();

    // Valor que aporta al hc total cada tipo de organizacion (por su clasificacion)
    List<ClasificacionOrg> tiposOrg = Arrays.asList(ClasificacionOrg.values());
    List<Double> porcentajesTipoOrg = new ArrayList<>();
    tiposOrg.forEach(tipo -> {
      double valor = RepositorioOrganizaciones.getInstance().HCTotal(tipo);
      porcentajesTipoOrg.add(valor);
    });

    Map<String, Object> model = new HashMap<>();
    //model.put("datos",datos);
    model.put("porcentajesTipoOrg", porcentajesTipoOrg);
    model.put("nombreAgente", nombreAgente);
    model.put("ubicacion", ubicacion);
    model.put("nombres", nombres);
    model.put("porcentajes", porcentajes);
    model.put("total", df.format(total.enKgCO2()));
    model.put("valoresHistorial", valoresHistorial);
    return new ModelAndView(model, "reportesAgSec.hbs");
  }

}
