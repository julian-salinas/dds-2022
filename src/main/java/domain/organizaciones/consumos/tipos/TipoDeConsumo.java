package domain.organizaciones.consumos.tipos;

import domain.organizaciones.consumos.Actividad;
import domain.organizaciones.consumos.Alcance;
import domain.organizaciones.consumos.Unidad;

public class TipoDeConsumo {
  protected Unidad unidad;
  protected Actividad actividad;
  protected Alcance alcance;
  protected FactorEmision fe;

  public void cargarFactorEmision(FactorEmision fe){
    if(unidad.equals(fe.getUnidad())){
      this.fe = fe;
    } else {
      throw new NoCoincidenUnidadesFEYTC();
    }
  }

}
