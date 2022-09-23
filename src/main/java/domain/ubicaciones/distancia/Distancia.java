package domain.ubicaciones.distancia;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import static domain.ubicaciones.distancia.UnidadDistancia.KM;
import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

@Embeddable
public class Distancia {
  @Column(name = "distancia_a_prox")
  private double valor;
  @Column(name = "unidad_distancia")
  @Enumerated(EnumType.STRING)
  private UnidadDistancia unidadDeDistancia;

  public Distancia() {}

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
