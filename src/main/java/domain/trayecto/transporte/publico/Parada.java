package domain.trayecto.transporte.publico;

import domain.database.PersistenceEntity;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Parada extends PersistenceEntity {
  private String nombre;
  @Embedded
  @Getter private Distancia distAproximaParada;

  public Parada() {}

  public Parada(String nombre, Distancia distProx) {
    this.nombre = nombre;
    this.distAproximaParada = distProx;
  }
}
