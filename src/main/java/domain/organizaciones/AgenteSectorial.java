package domain.organizaciones;

import domain.ubicaciones.SectorTerritorial;

import java.util.List;

public class AgenteSectorial {
  SectorTerritorial sectorTerritorial;

  public HC hC() {
    List<Organizacion>  orgDentroDeSectorT = sectorTerritorial.orgsDentroDeSector();
    double hcEnKgCO2 = orgDentroDeSectorT.stream().mapToDouble(org -> org.calculoHC().enKgCO2()).sum();
    return new HC(hcEnKgCO2, UnidadHC.kgCO2);
  }

}
