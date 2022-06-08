package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.COMBUSTIONFIJA;
import static domain.organizaciones.consumos.Alcance.DIRECTAS;
import static domain.organizaciones.consumos.Unidad.KG;

public class Carbon extends TipoDeConsumo {
  public Carbon() {
    unidad = KG;
    actividad = COMBUSTIONFIJA;
    alcance = DIRECTAS;
  }
}

