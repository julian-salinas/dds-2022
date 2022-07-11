package domain.organizaciones;

import domain.ubicaciones.SectorTerritorial;
import domain.repositorios.Organizaciones;

import java.util.List;

public class AgenteSectorial {
  SectorTerritorial sectorTerritorial;

  public void HCsectorT() {
    List<Organizacion>  orgDentroDeSectorT = Organizaciones.instance().inSectorTerritorial(sectorTerritorial);
    // double hcEnKgCO2 = orgDentroDeSectorT.stream().mapToDouble(org -> org.calculoHC().enKgCO2()).sum();
    // return new HCauxiliar(hcEnKgCO2, unidadHCauxiliar.kgCO2);
  }

}
