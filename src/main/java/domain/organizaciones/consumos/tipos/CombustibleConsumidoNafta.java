package domain.organizaciones.consumos.tipos;

import static domain.organizaciones.consumos.Actividad.COMBUSTIONMOVIL;
import static domain.organizaciones.consumos.Alcance.DIRECTAS;
import static domain.organizaciones.consumos.Unidad.LT;

public class CombustibleConsumidoNafta extends TipoDeConsumo {
  public CombustibleConsumidoNafta() {
    unidad = LT;
    actividad = COMBUSTIONMOVIL;
    alcance = DIRECTAS;
  }
}

