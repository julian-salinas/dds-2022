package domain.ubicaciones;

import domain.servicios.geodds.ServicioGeoDds;
import java.io.IOException;

public class Pais {
  private int id;
  private String nombre;
  private ServicioGeoDds apiClient;

  public Pais(String nombre) throws RuntimeException, IOException {
    this.apiClient = ServicioGeoDds.getInstancia();
    this.id = apiClient.verificarNombrePais(nombre);
    this.nombre = nombre.toUpperCase();
  }

}
