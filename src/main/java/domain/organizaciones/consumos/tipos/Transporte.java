package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.LOGISTICA_PRODUCTOS_RESIDUOS;
import static domain.organizaciones.consumos.Alcance.INDIRECTAS_EXTERNAS;

public class Transporte extends TipoDeConsumo {
  public Transporte() {
    unidad = null;
    actividad = LOGISTICA_PRODUCTOS_RESIDUOS;
    alcance = INDIRECTAS_EXTERNAS;
  }
}

