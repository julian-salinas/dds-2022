package domain.ubicaciones.distancia;

import static domain.ubicaciones.distancia.UnidadDistancia.KM;
import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

public class Distancia {
  private double valor;
  private UnidadDistancia unidadDeDistancia;

  public Distancia(double valor, UnidadDistancia unidadDeDistancia) {
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
