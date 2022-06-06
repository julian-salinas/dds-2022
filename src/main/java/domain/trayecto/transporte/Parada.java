package domain.trayecto.transporte;

import lombok.Getter;

public class Parada {

  private String nombre;
  @Getter private int distAproximaParada;

  public Parada(String nombre, int distProx) {
    this.nombre = nombre;
    this.distAproximaParada = distProx;
  }

}
