package domain.trayecto.transporte.nopublico;

import domain.servicios.geodds.excepciones.TimeoutException;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

@Entity(name = "servicio_contratado")
public class ServicioContratado extends MedioDeTransporte {
  @Embedded
  private TipoServicioContratado tipo;
  @Getter @Setter private double combustibleConsumidoPorKM; // No se que deberia poner en los que desconocemos
  //@Getter private String descripcion;

  public ServicioContratado() {}

  public ServicioContratado(TipoServicioContratado tipo, double combustible) {
    this.tipo = tipo;
    this.combustibleConsumidoPorKM = combustible;
    this.descripcion = this.toString();
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return true;
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
    // "Servicio Contratado: " +
    return tipo.getNombre().toUpperCase();
  }

}
