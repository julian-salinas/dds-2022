package presentacion.controladores;

import domain.organizaciones.Organizacion;
import domain.organizaciones.datos.actividades.DatosActividades;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import repositorios.RepositorioConsumos;
import repositorios.RepositorioOrganizaciones;
import repositorios.RepositorioUsuarios;
import org.apache.commons.io.FileUtils;
import presentacion.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MedicionesController {

  public ModelAndView index(Request request, Response response) {
    String username = request.session().attribute("usuario_logueado");

    if (username == null) {
      response.redirect("/login");
      return null;
    }

    Map<String, Object> model = new HashMap<>();
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();
    List<Organizacion> orgs = new ArrayList<>();
    orgs.add(org);
    List<TipoDeConsumo> consumos = RepositorioConsumos.getInstance().all();
    model.put("orgs", orgs);
    model.put("consumos", consumos);
    return new ModelAndView(model, "mediciones.hbs");
  }

  public ModelAndView postCsv(Request request, Response response) throws IOException, ServletException {
    request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
    InputStream inputStream = request.raw().getPart("archivoCSV").getInputStream();

    System.out.println("inputStream: " + inputStream);

    String pathToCSV = "upload/csvfile.csv";
    File targetFile = new File(pathToCSV);

    FileUtils.copyInputStreamToFile(inputStream, targetFile);
    String username = request.session().attribute("usuario_logueado");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();
    org.cargarMediciones(pathToCSV);

    RepositorioOrganizaciones.getInstance().update(org);

    response.redirect("/mediciones");
    return null;
  }

  public ModelAndView postManual(Request request, Response response) {

    String username = request.session().attribute("usuario_logueado");
    Usuario usuario = RepositorioUsuarios.getInstance().findByUsername(username);
    Organizacion org = usuario.getOrg();
    int tipoConsumoid = Integer.parseInt(request.queryParams("tipoConsumo"));
    String valor = request.queryParams("valor");
    String periodicidad = request.queryParams("periodicidad");
    String perImputacion = request.queryParams("perImputacion");

    String tipoConsumo = RepositorioConsumos.getInstance().get(tipoConsumoid).getTipo();

    DatosActividades datosActividades = new DatosActividades(tipoConsumo,
        valor, periodicidad, perImputacion);
    org.getDatosActividades().add(datosActividades);

    //RepositorioOrganizaciones.getInstance().update(org);
    RepositorioUsuarios.getInstance().update(usuario);

    response.redirect("/mediciones");
    return null;
  }

}
