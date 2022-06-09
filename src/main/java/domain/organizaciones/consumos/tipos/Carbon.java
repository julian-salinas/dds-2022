package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.COMBUSTION_FIJA;
import static domain.organizaciones.consumos.Alcance.DIRECTAS;
import static domain.organizaciones.consumos.Unidad.KG;

public class Carbon extends TipoDeConsumo {
  public Carbon() {
    unidad = KG;
    actividad = COMBUSTION_FIJA;
    alcance = DIRECTAS;
  }
}

