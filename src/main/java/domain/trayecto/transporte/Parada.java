package domain.trayecto.transporte;

import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;

public class Parada {

  private String nombre;
  @Getter private final Distancia distAproximaParada;


  public Parada(String nombre, Distancia distProx) {
    this.nombre = nombre;
    this.distAproximaParada = distProx;
  }
}
