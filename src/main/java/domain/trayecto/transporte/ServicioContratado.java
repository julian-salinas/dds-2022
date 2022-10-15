package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

@Entity(name = "servicio_contratado")
public class ServicioContratado extends MedioDeTransporte {
  @Transient
  @Getter private Ubicacion direccionInicio;
  @Transient
  @Getter private Ubicacion direccionFin;
  @Embedded
  private TipoServicioContratado tipo;
  @Getter @Setter private double combustibleConsumidoPorKM; // No se que deberia poner en los que desconocemos

  public ServicioContratado() {}

  public ServicioContratado(TipoServicioContratado tipo,
                            Ubicacion direccionInicio,
                            Ubicacion direccionFin,
                            double combustible) {
    this.tipo = tipo;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
    this.combustibleConsumidoPorKM = combustible;
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return true;
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
