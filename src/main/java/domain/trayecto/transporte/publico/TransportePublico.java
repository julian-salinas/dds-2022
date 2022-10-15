package domain.trayecto.transporte.publico;

import domain.trayecto.transporte.MedioDeTransporte;
import domain.trayecto.transporte.excepciones.ExcepcionParadasTransporteNoIncluidasEnLinea;
import domain.trayecto.transporte.excepciones.ExcepcionTipoTransporteNoIgualAtipoDeLinea;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "transporte_publico")
public class TransportePublico extends MedioDeTransporte {
  @Transient
  private Parada paradaInicio;
  @Transient
  private Parada paradaFin;
  @Enumerated(EnumType.STRING)
  private TipoTransportePublico tipo;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Linea linea;
  @Getter @Setter private double combustibleConsumidoPorKM = 0.0;

  private void validacionesTransportePublico(TipoTransportePublico tipo, Linea linea,
                                             Parada paradaInicio, Parada paradaFin) {

    if (!tipoYtipoDeLaLineaSonIguales(tipo, linea)) {
      throw new ExcepcionTipoTransporteNoIgualAtipoDeLinea();

    } else if (!paradasIncluidasEnLaLinea(paradaInicio, paradaFin, linea)) {
      throw new ExcepcionParadasTransporteNoIncluidasEnLinea();

    }
  }

  private boolean tipoYtipoDeLaLineaSonIguales(TipoTransportePublico tipo, Linea linea) {
    return linea.isTipo(tipo);
  }

  private boolean paradasIncluidasEnLaLinea(Parada paradaInicio, Parada paradaFin, Linea linea) {
    return linea.containsParada(paradaInicio) && linea.containsParada(paradaFin);
  }

  public TransportePublico() {}

  public TransportePublico(TipoTransportePublico tipo, Linea linea,
                           Parada paradaInicio, Parada paradaFin) {

    // Comentar esto para correr Workspace
    validacionesTransportePublico(tipo, linea, paradaInicio, paradaFin);
    this.tipo = tipo;
    this.linea = linea;
    this.paradaInicio = paradaInicio;
    this.paradaFin = paradaFin;

  }

  public Distancia distancia() {
    return linea.distanciaEntreParadas(paradaInicio, paradaFin);
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return false;
  }

}
