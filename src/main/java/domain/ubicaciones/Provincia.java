package domain.ubicaciones;

import domain.servicios.geodds.ServicioGeoDds;
import java.io.IOException;

public class Provincia {
  private int id;
  private String nombre;
  private Pais pais;
  private ServicioGeoDds apiClient;

  public Provincia(String nombre) throws RuntimeException, IOException {
    this.apiClient = ServicioGeoDds.getInstancia();

    // Hago esta cosa rara para obtener una provincia pero de la clase de la entidad provincia
    domain.servicios.geodds.entidades.Provincia responseProvincia = apiClient.verificarNombreProvincia(nombre);

    this.id = responseProvincia.getId();
    this.nombre = nombre.toUpperCase();
    this.pais = new Pais(responseProvincia.getPais().getNombre().toUpperCase(), responseProvincia.getPais().getId());
  }

}
