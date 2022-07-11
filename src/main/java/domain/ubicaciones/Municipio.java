package domain.ubicaciones;

import domain.servicios.geodds.ServicioGeoDds;

import java.io.IOException;

public class Municipio implements SectorTerritorial{
  private int id;
  private String nombre;
  private ServicioGeoDds apiClient;

  public Municipio(String nombre) throws IOException, RuntimeException {
    this.apiClient = ServicioGeoDds.getInstancia();
    this.id = this.apiClient.verificarNombreMunicipio(nombre);
    this.nombre = nombre.toUpperCase();
  }

}
