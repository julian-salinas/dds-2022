package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import lombok.Getter;

public class ServicioContratado extends MedioNoPublico {

  @Getter private Ubicacion direccionInicio;
  @Getter private Ubicacion direccionFin;
  private final TipoServicioContratado tipo;
  @Getter private double combustibleConsumidoPorKM; // No se que deberia poner en los que desconocemos

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
}
