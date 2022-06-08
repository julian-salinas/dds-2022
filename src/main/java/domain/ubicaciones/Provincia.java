package domain.ubicaciones;

import domain.servicios.geodds.ServicioGeoDds;
import java.io.IOException;

public class Provincia {
  private int id;
  private String nombre;
  private ServicioGeoDds apiClient;

  public Provincia(String nombre) throws RuntimeException, IOException {
    this.apiClient = ServicioGeoDds.getInstancia();

    this.id = this.apiClient.verificarNombreProvincia(nombre);
    this.nombre = nombre.toUpperCase();
  }

}
