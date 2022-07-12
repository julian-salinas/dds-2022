package domain.trayecto.transporte;

import domain.trayecto.transporte.excepciones.ExcepcionParadasTransporteNoIncluidasEnLinea;
import domain.trayecto.transporte.excepciones.ExcepcionTipoTransporteNoIgualAtipoDeLinea;
import domain.ubicaciones.Distancia;
import lombok.Getter;

public class TransportePublico implements MedioDeTransporte {

  private Parada paradaInicio;
  private Parada paradaFin;
  private TipoTransportePublico tipo;
  private Linea linea;
  @Getter private double combustibleConsumidoPorKM = 0;

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

  public TransportePublico(TipoTransportePublico tipo, Linea linea,
                           Parada paradaInicio, Parada paradaFin) {

    validacionesTransportePublico(tipo, linea, paradaInicio, paradaFin);
    this.tipo = tipo;
    this.linea = linea;
    this.paradaInicio = paradaInicio;
    this.paradaFin = paradaFin;

  }

  public Distancia getDistancia() {
    return linea.distanciaEntreParadas(paradaInicio, paradaFin);
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return false;
  }

}
