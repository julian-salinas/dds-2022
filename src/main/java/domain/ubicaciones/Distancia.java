package domain.ubicaciones;

import static domain.ubicaciones.UnidadDeDistancia.MTS;

public class Distancia {
  public double valor;
  public UnidadDeDistancia unidadDeDistancia;


  public Distancia(double valor, UnidadDeDistancia unidadDeDistancia) {
    this.valor = valor;
    this.unidadDeDistancia = unidadDeDistancia;
  }


  public double valorEnMetros() {
    if (this.unidadDeDistancia == MTS) {
      return this.valor;
    }
    else {
      return this.valor * 1000;
    }
  }
}
