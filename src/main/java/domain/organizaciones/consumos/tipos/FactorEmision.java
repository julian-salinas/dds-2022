package domain.organizaciones.consumos.tipos;

import domain.organizaciones.consumos.Unidad;
import lombok.Getter;

@Getter
public class FactorEmision {
  private int valor;
  private Unidad unidad;

  public FactorEmision(int valor, Unidad unidad){
    this.valor = valor;
    this.unidad = unidad;
  }
}
