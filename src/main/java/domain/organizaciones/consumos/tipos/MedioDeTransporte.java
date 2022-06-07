package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.PRODUCTOSRESIDUOS;
import static domain.organizaciones.consumos.Alcance.INDIRECTASEXTERNAS;

public class MedioDeTransporte extends TipoDeConsumo {
  public MedioDeTransporte() {
    unidad = null;
    actividad = PRODUCTOSRESIDUOS;
    alcance = INDIRECTASEXTERNAS;
  }
}

