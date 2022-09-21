package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Bicicleta extends MedioNoPublico {
  @Transient
  @Getter private Ubicacion direccionInicio;
  @Transient
  @Getter private Ubicacion direccionFin;
  @Getter @Setter private double combustibleConsumidoPorKM = 0.0;

  public Bicicleta() {}

  public Bicicleta(Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

}
