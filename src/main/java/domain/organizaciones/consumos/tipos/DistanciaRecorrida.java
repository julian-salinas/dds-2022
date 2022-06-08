package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.PRODUCTOSRESIDUOS;
import static domain.organizaciones.consumos.Alcance.INDIRECTASEXTERNAS;
import static domain.organizaciones.consumos.Unidad.KM;

public class DistanciaRecorrida extends TipoDeConsumo {
  public DistanciaRecorrida() {
    unidad = KM;
    actividad = PRODUCTOSRESIDUOS;
    alcance = INDIRECTASEXTERNAS;
  }
}
