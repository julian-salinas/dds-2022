package domain.trayecto.transporte.nopublico;

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

  public ServicioContratado() {}

  public ServicioContratado(TipoServicioContratado tipo, double combustible) {
    this.tipo = tipo;
    this.combustibleConsumidoPorKM = combustible;
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return true;
  }

  public Distancia distancia(Ubicacion ubicacionInicio, Ubicacion ubicacionFin) {
    try {
      return ubicacionInicio.calcularDistanciaA(ubicacionFin);
    } catch (IOException e) {
      // bruh
      // No le pegues a la API, pegame a mi, me lo merezco, te falle este try tan simple.
      // Por mi, te dejo de funcar la app de la nada y probablemente no tenes ni idea de q yo lo cause.
      e.printStackTrace();
      return new Distancia(-1.0, MTS);
    }
  }

  @Override
  public String toString() {
    // "Servicio Contratado: " +
    return tipo.getNombre().toUpperCase();
  }

}
