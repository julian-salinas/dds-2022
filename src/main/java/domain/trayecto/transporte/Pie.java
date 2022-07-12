package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import lombok.Getter;

public class Pie extends MedioNoPublico {
  @Getter private Ubicacion direccionInicio;
  @Getter private Ubicacion direccionFin;
  @Getter private double combustibleConsumidoPorKM = 0;

  public Pie(Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

}
