package domain.ubicaciones;

import domain.organizaciones.Organizacion;
import domain.repositorios.RepoOrganizaciones;
import domain.servicios.geodds.ServicioGeoDds;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

public class Provincia implements SectorTerritorial{
  @Getter private int id;
  @Getter private String nombre;
  //@Getter private Pais pais; <----------- no lo pongo por ahora porque no lo usamos
  private ServicioGeoDds apiClient;

  public Provincia(String nombre, ServicioGeoDds apiClient) throws RuntimeException, IOException {
    //this.apiClient = ServicioGeoDds.getInstancia();
    this.apiClient = apiClient;
    this.id = this.apiClient.verificarNombreProvincia(nombre);
    this.nombre = nombre.toUpperCase();
  }

  @Override
  public List<Organizacion> orgsDentroDeSector() {
    return RepoOrganizaciones.instance().inProvincia(this);
  }
}
