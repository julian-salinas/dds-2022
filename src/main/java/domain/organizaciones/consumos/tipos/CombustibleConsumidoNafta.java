package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.COMBUSTION_MOVIL;
import static domain.organizaciones.consumos.Alcance.DIRECTAS;
import static domain.organizaciones.consumos.Unidad.LT;

public class CombustibleConsumidoNafta extends TipoDeConsumo {
  public CombustibleConsumidoNafta() {
    unidad = LT;
    actividad = COMBUSTION_MOVIL;
    alcance = DIRECTAS;
  }
}

