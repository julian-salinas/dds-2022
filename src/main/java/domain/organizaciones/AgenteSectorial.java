package domain.organizaciones;

import domain.ubicaciones.SectorTerritorial;

import java.util.List;

public class AgenteSectorial {
  SectorTerritorial sectorTerritorial;

  public AgenteSectorial(SectorTerritorial sectorTerritorial) {
    this.sectorTerritorial = sectorTerritorial;
  }

  public HC hcSectorMensual() {
    List<Organizacion> orgDentroDeSectorT = sectorTerritorial.orgsDentroDeSector();
    double hcEnKgCO2 = orgDentroDeSectorT.stream().mapToDouble(org -> org.hcMensual().enKgCO2()).sum();
    return new HC(hcEnKgCO2, UnidadHC.kgCO2);
  }

  public HC hcSectorAnual() {
    List<Organizacion> orgDentroDeSectorT = sectorTerritorial.orgsDentroDeSector();
    double hcEnKgCO2 = orgDentroDeSectorT.stream().mapToDouble(org -> org.hcAnual().enKgCO2()).sum();
    return new HC(hcEnKgCO2, UnidadHC.kgCO2);
  }

}
