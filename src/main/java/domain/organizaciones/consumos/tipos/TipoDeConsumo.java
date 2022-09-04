package domain.organizaciones.consumos.tipos;

import domain.organizaciones.consumos.Actividad;
import domain.organizaciones.consumos.Alcance;
import domain.organizaciones.consumos.Unidad;
import lombok.Getter;

public class TipoDeConsumo {
  private Unidad unidad;
  private Actividad actividad;
  private Alcance alcance;
  @Getter private FactorEmision fe;

  public TipoDeConsumo(Unidad unidad, Actividad actividad, Alcance alcance) {
    this.unidad = unidad;
    this.actividad = actividad;
    this.alcance = alcance;
  }

  public void cargarFactorEmision(FactorEmision fe){
    if(unidad.equals(fe.getUnidad())) {
      this.fe = fe;
    } else {
      throw new NoCoincidenUnidadesFEYTC();
    }
  }

}
