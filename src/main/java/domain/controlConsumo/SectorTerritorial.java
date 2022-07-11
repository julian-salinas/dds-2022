package domain.controlConsumo;

import domain.repositorios.RepoOrganizaciones;
import domain.organizaciones.Organizacion;
import domain.ubicaciones.Territorio;
import domain.ubicaciones.Ubicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class SectorTerritorial {
  RepoOrganizaciones repo;

  List<Territorio> territorios = new ArrayList<>();

  public List<Organizacion> organizacionesDelSector() {
    return repo.getOrganizaciones().stream().filter(organizacion -> contieneUbicacion(organizacion.getUbicacion())).collect(Collectors.toList());
  }

  private Boolean contieneUbicacion(Ubicacion ubicacion){
    return true; // quiero que controle con la API si la ubicacion esta dentro de la provincia/municipio que corresponde
  }
}
