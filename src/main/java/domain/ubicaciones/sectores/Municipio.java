package domain.ubicaciones.sectores;


import domain.servicios.geodds.ServicioGeoDds;
import java.io.IOException;
import lombok.Getter;

public class Municipio implements SectorTerritorial {
  @Getter private int id;
  @Getter private String nombre;
  @Getter private Provincia provincia;
  private ServicioGeoDds apiClient;

  @Deprecated
  public Municipio(String nombre, ServicioGeoDds apiClient) throws IOException, RuntimeException {
    this.apiClient = apiClient;
    //this.id = this.apiClient.verificarNombreMunicipio(nombre);
    this.provincia = new Provincia(apiClient.nombreProvincia(id), apiClient);
    this.nombre = nombre.toUpperCase();
  }

  public Municipio(String nombre, Provincia provincia, ServicioGeoDds apiClient) throws IOException, RuntimeException {
    this.apiClient = apiClient;
    this.id = this.apiClient.verificarNombreMunicipio(nombre, provincia.getId());
    this.nombre = nombre.toUpperCase();
    this.provincia = provincia;
  }

}
