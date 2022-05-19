package dominio.organizacionymiembros;

public class TrabajoMiembro {
  Organizacion organizacion;
  Sector sector;

  public TrabajoMiembro(Organizacion org, Sector sector) {
    this.organizacion = org;
    this.sector = sector;
  }

}
