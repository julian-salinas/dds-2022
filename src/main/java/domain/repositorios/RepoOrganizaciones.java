package domain.repositorios;

import domain.organizaciones.Organizacion;
import domain.ubicaciones.Municipio;
import domain.ubicaciones.Provincia;
import domain.ubicaciones.SectorTerritorial;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RepoOrganizaciones {
  private static final RepoOrganizaciones INSTANCE = new RepoOrganizaciones();
  private List<Organizacion> organizaciones;

  public static RepoOrganizaciones instance() {
    return INSTANCE;
  }

  public void agregarOrganizacion(Organizacion ... organizaciones) {
    Collections.addAll(this.organizaciones, organizaciones);
  }

  public List<Organizacion> getOrganizaciones() {
    return this.organizaciones;
  }

  public List<Organizacion> inMunicipio(Municipio municipio) {
    return organizaciones
        .stream()
        .filter(org -> org.sectorMunicipio().equals(municipio) ||
            (org.sectorMunicipio().getId() == municipio.getId() &&
                Objects.equals(org.sectorMunicipio().getNombre(), municipio.getNombre()))
        )
        .collect(Collectors.toList());
  }

  public List<Organizacion> inProvincia(Provincia provincia) {
    return organizaciones
        .stream()
        .filter(org -> org.sectorProvincia().equals(provincia) ||
            (org.sectorProvincia().getId() == provincia.getId() &&
                Objects.equals(org.sectorProvincia().getNombre(), provincia.getNombre()))
        )
        .collect(Collectors.toList());
  }

}
