package domain.controlConsumo;

public class AgenteSectorial {
  SectorTerritorial sector;

  public double calcularHuellaSectorial() {
    return sector.organizacionesDelSector().stream().mapToDouble(organizacion -> organizacion.calculoHC()).sum();
  }
}
