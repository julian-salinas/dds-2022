package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.organizaciones.datos.actividades.DatosActividades;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.repositorios.RepositorioOrganizaciones;
import domain.repositorios.RepositorioUsuarios;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.stream.Collectors;

public class MedicionesController {

  public ModelAndView index(Request request, Response response) {
    String username = request.cookie("username");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();
    return new ModelAndView(org, "mediciones.hbs");
  }

  public ModelAndView postCsv(Request request, Response response) {
    // TODO: Arreglarlo, porque aca llega el path NO completo (solo nombre de archivo), entonces explota
    String username = request.cookie("username");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();
    String pathCSV = request.queryParams("archivoCSV");

    org.cargarMediciones(pathCSV);
    RepositorioOrganizaciones.getInstance().update(org);

    response.redirect("/mediciones");
    return null;
  }

  public ModelAndView postManual(Request request, Response response) {

    String username = request.cookie("username");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();
    String tipoConsumo = request.queryParams("tipoConsumo");
    String valor = request.queryParams("valor");
    String periodicidad = request.queryParams("periodicidad");
    String perImputacion = request.queryParams("perImputacion");

    DatosActividades datosActividades = new DatosActividades(tipoConsumo,
        valor, periodicidad, perImputacion);
    org.getDatosActividades().add(datosActividades);

    //RepositorioOrganizaciones.getInstance().update(org);
    RepositorioUsuarios.getInstance().update(usuario);

    response.redirect("/mediciones");
    return null;
  }

  public ModelAndView postFe(Request request, Response response) {

    String username = request.cookie("username");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();
    int idDA = Integer.parseInt(request.queryParams("idDA"));
    UnidadConsumo unidadFE = UnidadConsumo.valueOf(request.queryParams("unidadFE"));
    double valorFE = Double.parseDouble(request.queryParams("valorFE"));

    FactorEmision fe = new FactorEmision(valorFE, unidadFE);
    DatosActividades da = org
        .getDatosActividades()
        .stream()
        .filter(datosActividades -> datosActividades.getId()==idDA)
        .collect(Collectors.toList())
        .get(0);

    da.cargarFactorEmision(fe);
    RepositorioOrganizaciones.getInstance().update(org);

    response.redirect("/mediciones");
    return null;
  }

}
