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

  public Pais(String nombre, int id) {
    /**
     * Este método tiene el objetivo de crear un país sin hacer un request a la api, solo debe usarse
     * cuando el ID del país y el nombre son conocidos.
     */
    this.apiClient = ServicioGeoDds.getInstancia();
    this.id = id;
    this.nombre = nombre;
  }


}
