package domain.ubicaciones.sectores;

import domain.organizaciones.Organizacion;
import domain.repositorios.RepoOrganizaciones;
import domain.servicios.geodds.ServicioGeoDds;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

public class Municipio implements SectorTerritorial {
  @Getter private int id;
  @Getter private String nombre;
  @Getter private Provincia provincia;
  private ServicioGeoDds apiClient;

  public Municipio(String nombre, ServicioGeoDds apiClient) throws IOException, RuntimeException {
    //this.apiClient = ServicioGeoDds.getInstancia();
    this.apiClient = apiClient;
    this.id = this.apiClient.verificarNombreMunicipio(nombre);
    this.provincia = new Provincia(apiClient.nombreProvincia(id), apiClient);
    this.nombre = nombre.toUpperCase();
  }

  @Override
  public List<Organizacion> orgsDentroDeSector() {
    return RepoOrganizaciones.instance().inMunicipio(this);
  }
}
