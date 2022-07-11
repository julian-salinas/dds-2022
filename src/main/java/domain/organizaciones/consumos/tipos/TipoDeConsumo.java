package domain.organizaciones.consumos.tipos;

import domain.organizaciones.consumos.Actividad;
import domain.organizaciones.consumos.Alcance;
import domain.organizaciones.consumos.Unidad;

import java.util.Optional;

public class TipoDeConsumo {
  private Optional<Unidad> unidad;
  private Actividad actividad;
  private Alcance alcance;
  private FactorEmision fe;

  public TipoDeConsumo(Optional<Unidad> unidad, Actividad actividad, Alcance alcance) {
    this.unidad = unidad;
    this.actividad = actividad;
    this.alcance = alcance;
  }

  public void cargarFactorEmision(FactorEmision fe){
    if(unidad.isPresent() && unidad.get().equals(fe.getUnidad())) {
      this.fe = fe;
    } else {
      throw new NoCoincidenUnidadesFEYTC();
    }
  }

  public FactorEmision getFe() {
    return fe;
  }
}
