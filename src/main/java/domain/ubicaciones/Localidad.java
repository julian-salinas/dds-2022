package domain.ubicaciones;

import domain.servicios.geodds.ServicioGeoDds;
import java.io.IOException;
import lombok.Getter;

@Getter
public class Localidad {
  @Getter private int id;
  private String nombre;
  private ServicioGeoDds apiClient;

  public Localidad(String nombre) throws RuntimeException, IOException {
    this.apiClient = ServicioGeoDds.getInstancia();
    this.id = this.apiClient.verificarNombreLocalidad(nombre);
    this.nombre = nombre.toUpperCase();
  }

}
