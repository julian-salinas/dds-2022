package domain.miembros;

import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;

public class TrabajoMiembro {
  Organizacion organizacion;
  Sector sector;

  public TrabajoMiembro(Organizacion org, Sector sector) {
    this.organizacion = org;
    this.sector = sector;
  }

}
