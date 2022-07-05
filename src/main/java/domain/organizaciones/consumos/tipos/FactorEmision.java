package domain.organizaciones.consumos.tipos;

import domain.organizaciones.consumos.Unidad;

public class FactorEmision {
  private int valor;
  private Unidad unidad;

  public FactorEmision(int valor, Unidad unidad){
    this.valor = valor;
    this.unidad = unidad;
  }

  public Unidad getUnidad() {
    return unidad;
  }
}
