package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import lombok.Getter;

public class Bicicleta extends MedioNoPublico {
  @Getter private Ubicacion direccionInicio;
  @Getter private Ubicacion direccionFin;

  public Bicicleta(Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

}
