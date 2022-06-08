package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.ELECTRICIDAD;
import static domain.organizaciones.consumos.Alcance.INDIRECTAS_ELECTRICIDAD;
import static domain.organizaciones.consumos.Unidad.KWH;

public class Electricidad extends TipoDeConsumo {
  public Electricidad() {
    unidad = KWH;
    actividad = ELECTRICIDAD;
    alcance = INDIRECTAS_ELECTRICIDAD;
  }
}

