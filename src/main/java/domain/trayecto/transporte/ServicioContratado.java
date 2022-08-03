package domain.trayecto.transporte;

import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.Ubicacion;
import lombok.Getter;

public class ServicioContratado extends MedioNoPublico {

  @Getter private Ubicacion direccionInicio;
  @Getter private Ubicacion direccionFin;
  private final TipoServicioContratado tipo;
  @Getter private double combustibleConsumidoPorKM; // No se que deberia poner en los que desconocemos
  @Getter private ServicioGeoDds apiClient;

  public ServicioContratado(TipoServicioContratado tipo,
                            Ubicacion direccionInicio,
                            Ubicacion direccionFin,
                            ServicioGeoDds apiClient,
                            double combustible) {
    this.tipo = tipo;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
    this.apiClient = apiClient;
    this.combustibleConsumidoPorKM = combustible;
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return true;
  }
}
