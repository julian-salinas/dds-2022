package domain.trayecto.transporte;

import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.Ubicacion;
import lombok.Getter;

public class Pie extends MedioNoPublico {
  @Getter private Ubicacion direccionInicio;
  @Getter private Ubicacion direccionFin;
  @Getter private double combustibleConsumidoPorKM = 0.0;
  @Getter private ServicioGeoDds apiClient;

  public Pie(Ubicacion direccionInicio, Ubicacion direccionFin,
                   ServicioGeoDds apiClient) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
    this.apiClient = apiClient;
  }

}
