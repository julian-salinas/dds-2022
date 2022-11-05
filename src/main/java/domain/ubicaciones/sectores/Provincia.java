package domain.ubicaciones.sectores;

import domain.servicios.geodds.ServicioGeoDds;
import java.io.IOException;
import lombok.Getter;


public class Provincia implements SectorTerritorial {
  @Getter private int id;
  @Getter private String nombre;
  @Getter private Pais pais; //<----------- no lo pongo por ahora porque no lo usamos
  private ServicioGeoDds apiClient;

  @Deprecated
  public Provincia(String nombre, ServicioGeoDds apiClient) throws RuntimeException, IOException {
    //this.apiClient = ServicioGeoDds.getInstancia();
    this.apiClient = apiClient;
    //this.id = this.apiClient.verificarNombreProvincia(nombre);
    this.nombre = nombre.toUpperCase();
  }

  public Provincia(String nombre, Pais pais, ServicioGeoDds apiClient) throws RuntimeException, IOException {
    //this.apiClient = ServicioGeoDds.getInstancia();
    this.apiClient = apiClient;
    this.id = this.apiClient.verificarNombreProvincia(nombre, pais.getId());
    this.nombre = nombre.toUpperCase();
    this.pais = pais;
  }

}
