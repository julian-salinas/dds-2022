package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import lombok.Getter;

public class ServicioContratado extends MedioNoPublico {

  @Getter private Ubicacion direccionInicio;
  @Getter private Ubicacion direccionFin;
  private final TipoServicioContratado tipo;
  @Getter private double combustibleConsumidoPorKM = 0; // No se que deberia poner en los que desconocemos

  public ServicioContratado(TipoServicioContratado tipo,
                            Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.tipo = tipo;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return true;
  }
}
