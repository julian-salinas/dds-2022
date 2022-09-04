package domain.organizaciones.datos.actividades.tipos;

import domain.organizaciones.datos.actividades.UnidadConsumo;
import lombok.Getter;
import lombok.Setter;

@Getter
public class FactorEmision {
  @Setter private double valor;
  private UnidadConsumo unidad;

  public FactorEmision(double valor, UnidadConsumo unidad){
    this.valor = valor;
    this.unidad = unidad;
  }
}
