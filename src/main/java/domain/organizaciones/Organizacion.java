package domain.organizaciones;

import domain.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.miembros.Miembro;
import domain.ubicaciones.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class Organizacion {
  private final String razonSocial;
  private TipoOrganizacion tipo;
  private Ubicacion ubicacion;
  private final List<Sector> sectores = new ArrayList<>();
  private ClasificacionOrganizacion clasificacion;

  public Organizacion(String razonSocial, TipoOrganizacion tipo,
                      Ubicacion ubicacion, ClasificacionOrganizacion clasificacion) {
    this.razonSocial = razonSocial;
    this.tipo = tipo;
    this.ubicacion = ubicacion;
    this.clasificacion = clasificacion;
  }

  public boolean containsSector(Sector sector) {
    return sectores.contains(sector);
  }

  public void agregarSector(Sector sector) {
    sector.setOrgAlaQuePertenezco(this);
    sectores.add(sector);
  }

  public void aceptarVinculacionDeTrabajador(Miembro miembro, Sector sector) {
    if (sector.containsMiembroParaAceptar(miembro)) {
      sector.sacarMiembroParaAceptar(miembro);
      sector.agregarMiembro(miembro);
    } else {
      throw new ExcepcionNoExisteElMiembroAacptarEnLaOrg();
    }
  }

}
