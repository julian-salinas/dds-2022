package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.COMBUSTION_FIJA;
import static domain.organizaciones.consumos.Alcance.DIRECTAS;
import static domain.organizaciones.consumos.Unidad.M3;

public class GasNatural extends TipoDeConsumo {
  public GasNatural() {
    unidad = M3;
    actividad = COMBUSTION_FIJA;
    alcance = DIRECTAS;
  }
}

