package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.LOGISTICA_PRODUCTOS_RESIDUOS;
import static domain.organizaciones.consumos.Alcance.INDIRECTAS_EXTERNAS;
import static domain.organizaciones.consumos.Unidad.KM;

public class DistanciaRecorrida extends TipoDeConsumo {
  public DistanciaRecorrida() {
    unidad = KM;
    actividad = LOGISTICA_PRODUCTOS_RESIDUOS;
    alcance = INDIRECTAS_EXTERNAS;
  }
}
