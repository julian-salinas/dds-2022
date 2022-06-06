package domain.trayecto.transporte;

import domain.trayecto.transporte.excepciones.ExcepcionParadasTransporteNoIncluidasEnLinea;
import domain.trayecto.transporte.excepciones.ExcepcionTipoTransporteNoIgualAtipoDeLinea;

public class TransportePublico implements MedioDeTransporte {

  private Parada paradaInicio;
  private Parada paradaFin;
  private TipoTransportePublico tipo;
  private Linea linea;

  private boolean tipoYtipoDeLaLineaSonIguales(TipoTransportePublico tipo, Linea linea) {
    return linea.isTipo(tipo);
  }

  private boolean paradasIncluidasEnLaLinea(Parada paradaInicio, Parada paradaFin, Linea linea) {
    return linea.containsParadas(paradaInicio) && linea.containsParadas(paradaFin);
  }

  public TransportePublico(TipoTransportePublico tipo, Linea linea,
                           Parada paradaInicio, Parada paradaFin) {
    if (tipoYtipoDeLaLineaSonIguales(tipo, linea)
        && paradasIncluidasEnLaLinea(paradaInicio, paradaFin, linea)) {

      this.tipo = tipo;
      this.linea = linea;
      this.paradaInicio = paradaInicio;
      this.paradaFin = paradaFin;

    } else if (!tipoYtipoDeLaLineaSonIguales(tipo, linea)) {
      throw new ExcepcionTipoTransporteNoIgualAtipoDeLinea();

    } else if (!paradasIncluidasEnLaLinea(paradaInicio, paradaFin, linea)) {
      throw new ExcepcionParadasTransporteNoIncluidasEnLinea();
    }
  }

}
