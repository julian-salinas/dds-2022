package domain.ubicaciones.sectores;

import domain.servicios.geodds.ServicioGeoDds;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.IOException;

public class Pais {
  @Id
  private int id;
  private String nombre;
  @Transient
  private ServicioGeoDds apiClient;

  public Pais(String nombre, ServicioGeoDds apiClient) throws RuntimeException, IOException {
    //this.apiClient = ServicioGeoDds.getInstancia();
    this.apiClient = apiClient;
    this.id = apiClient.verificarNombrePais(nombre);
    this.nombre = nombre.toUpperCase();
  }

}
