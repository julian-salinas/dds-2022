package domain.trayecto.transporte.publico;

import domain.database.PersistenceEntity;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;

import javax.persistence.*;

@Entity
public class Parada extends PersistenceEntity {
  private String nombre;
  @Embedded
  @Getter private Distancia distAproximaParada;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "ubicacion_parada")
  @Getter private Ubicacion ubicacionParada;

  public Parada() {}

  public Parada(String nombre, Ubicacion ubicacionParada, Distancia distProx) {
    this.nombre = nombre;
    this.distAproximaParada = distProx;
    this.ubicacionParada = ubicacionParada;
  }
}
