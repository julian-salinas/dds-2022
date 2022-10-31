package domain.ubicaciones.sectores;

import domain.organizaciones.Organizacion;
import domain.repositorios.RepositorioOrganizaciones;
import domain.servicios.geodds.ServicioGeoDds;
import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.IOException;
import java.util.List;

public class Municipio implements SectorTerritorial {
  @Getter private int id;
  @Getter private String nombre;
  @Getter private Provincia provincia;
  private ServicioGeoDds apiClient;

  public Municipio(String nombre, ServicioGeoDds apiClient) throws IOException, RuntimeException {
    this.apiClient = apiClient;
    this.id = this.apiClient.verificarNombreMunicipio(nombre);
    this.provincia = new Provincia(apiClient.nombreProvincia(id), apiClient);
    this.nombre = nombre.toUpperCase();
  }

  public Municipio(String nombre, Provincia provincia, ServicioGeoDds apiClient) throws IOException, RuntimeException {
    this.apiClient = apiClient;
    this.id = this.apiClient.verificarNombreMunicipio(nombre, provincia.getId());
    this.nombre = nombre.toUpperCase();
    this.provincia = provincia;
  }

  @Override
  public List<Organizacion> orgsDentroDeSector() {
    return RepositorioOrganizaciones.getInstance().inMunicipio(this);
  }
}
