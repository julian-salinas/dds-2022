package domain.repositorios;

import domain.notificaciones.contactos.Contacto;
import domain.organizaciones.Organizacion;
import domain.ubicaciones.sectores.Municipio;
import domain.ubicaciones.sectores.Provincia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;

public class RepoOrganizaciones {
  private static final RepoOrganizaciones INSTANCE = new RepoOrganizaciones();
  @Getter private List<Organizacion> organizaciones = new ArrayList<>();

  public static RepoOrganizaciones instance() {
    return INSTANCE;
  }

  public void agregarOrganizaciones(Organizacion ... organizaciones) {
    Collections.addAll(this.organizaciones, organizaciones);
  }

  public void limpiar() {
    organizaciones = new ArrayList<>();
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

  /**
   * Obtener todos los contactos de todas las organizaciones
   */
  public List<Contacto> getContactos() {
    return this.organizaciones.stream()
            .map(Organizacion::getContactos)
            .flatMap(List::stream)
            .collect(Collectors.toList());
  }

}
