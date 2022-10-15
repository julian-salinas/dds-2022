package domain.trayecto.transporte.nopublico;

import domain.trayecto.transporte.MedioDeTransporte;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.IOException;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

@Entity
public class Bicicleta extends MedioDeTransporte {
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

  @Override
  public Boolean admiteTrayectoCompartido() {
    return false;
  }

  public Distancia distancia() {
    try {
      return this.getDireccionInicio().calcularDistanciaA(this.getDireccionFin());
    } catch (IOException e) {
      // bruh
      e.printStackTrace();
      return new Distancia(-1.0, MTS);
    }
  }

}
