package domain.ubicaciones;

import domain.servicios.geodds.ServicioGeoDds;
import java.io.IOException;
import lombok.Getter;

public class Localidad {
  @Getter private int id;
  @Getter private String nombre;
  @Getter private Municipio municipio;
  private ServicioGeoDds apiClient;

  public Localidad(String nombre) throws RuntimeException, IOException {
    this.apiClient = ServicioGeoDds.getInstancia();
    this.id = this.apiClient.verificarNombreLocalidad(nombre);
    this.municipio = new Municipio(apiClient.nombreMunicipio(id));
    this.nombre = nombre.toUpperCase();
  }

}
