package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import lombok.Getter;

public class ServicioContratado extends MedioNoPublico {

  @Getter private Ubicacion direccionInicio;
  @Getter private Ubicacion direccionFin;
  private final TipoServicioContratado tipo;

  public ServicioContratado(TipoServicioContratado tipo,
                            Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.tipo = tipo;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

}
