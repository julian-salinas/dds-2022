package domain.trayecto.transporte.nopublico;

import domain.servicios.geodds.excepciones.TimeoutException;
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
public class Pie extends MedioDeTransporte {
  @Getter @Setter private double combustibleConsumidoPorKM = 0.0;
  //@Getter private String descripcion;

  public Pie() {
    this.descripcion = this.toString();
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return false;
  }

  public Distancia distancia(Ubicacion ubicacionInicio, Ubicacion ubicacionFin) {
    try {
      return ubicacionInicio.calcularDistanciaA(ubicacionFin);
    } catch (IOException e) {
      throw new TimeoutException("Timeout");
    }
  }

  @Override
  public String toString() {
    return "PIE";
  }

}
