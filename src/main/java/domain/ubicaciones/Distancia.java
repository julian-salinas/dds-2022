package domain.ubicaciones;

import static domain.ubicaciones.UnidadDeDistancia.KM;
import static domain.ubicaciones.UnidadDeDistancia.MTS;

public class Distancia {
  private double valor;
  private UnidadDeDistancia unidadDeDistancia;

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

  public double valorEnKm() {
    if (this.unidadDeDistancia == KM) {
      return this.valor;
    }
    else {
      return this.valor / 1000;
    }
  }

}
