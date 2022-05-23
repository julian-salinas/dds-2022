package domain.trayecto.transporte;

import domain.trayecto.transporte.excepciones.ExcepcionParadasTransporteNoIncluidasEnLinea;
import domain.trayecto.transporte.excepciones.ExcepcionTipoTransporteNoIgualAtipoDeLinea;

public class TransportePublico extends MedioPublico {

  TipoTransportePublico tipo;
  Linea linea;

  private boolean tipoYtipoDeLaLineaSonIguales(TipoTransportePublico tipo, Linea linea) {
    return linea.getTipo() == tipo;
  }

  private boolean paradasIncluidasEnLaLinea(Parada paradaInicio, Parada paradaFin, Linea linea) {
    return linea.getParadas().contains(paradaInicio) && linea.getParadas().contains(paradaFin);
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
      // throw exception "El tipo no es el mismo tipo de la linea"
      throw new ExcepcionTipoTransporteNoIgualAtipoDeLinea();
    } else if (!paradasIncluidasEnLaLinea(paradaInicio, paradaFin, linea)) {
      // throw exception "Las paradas no forman parte de la linea"
      throw new ExcepcionParadasTransporteNoIncluidasEnLinea();
    }
  }

}
