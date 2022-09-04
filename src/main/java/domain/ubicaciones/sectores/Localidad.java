package domain.ubicaciones.sectores;

import domain.servicios.geodds.ServicioGeoDds;
import java.io.IOException;

import lombok.Getter;

public class Localidad {
  @Getter private int id;
  @Getter private String nombre;
  @Getter private Municipio municipio;
  private ServicioGeoDds apiClient;

  public Localidad(String nombre, ServicioGeoDds apiClient) throws RuntimeException, IOException {
    //this.apiClient = ServicioGeoDds.getInstancia();
    this.apiClient = apiClient;
    this.id = this.apiClient.verificarNombreLocalidad(nombre);
    this.municipio = new Municipio(apiClient.nombreMunicipio(id), apiClient);
    this.nombre = nombre.toUpperCase();
  }

}
