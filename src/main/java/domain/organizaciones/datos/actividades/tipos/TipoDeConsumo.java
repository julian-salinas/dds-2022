package domain.organizaciones.datos.actividades.tipos;

import domain.organizaciones.datos.actividades.Actividad;
import domain.organizaciones.datos.actividades.Alcance;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import lombok.Getter;

public class TipoDeConsumo {
  private UnidadConsumo unidad;
  private Actividad actividad;
  private Alcance alcance;
  @Getter private FactorEmision fe;

  public TipoDeConsumo(UnidadConsumo unidad, Actividad actividad, Alcance alcance) {
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
