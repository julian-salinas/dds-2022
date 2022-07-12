package domain.ubicaciones;

import domain.organizaciones.Organizacion;
import java.util.List;

public interface SectorTerritorial {
  List<Organizacion> orgsDentroDeSector();
}
