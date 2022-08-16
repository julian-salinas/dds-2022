package domain.trayecto.transporte;

import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.Ubicacion;
import lombok.Getter;

public class VehiculoParticular extends MedioNoPublico {

  @Getter private Ubicacion direccionInicio;
  @Getter private Ubicacion direccionFin;
  private final TipoDeVehiculo tipoVehiculo;
  private TipoDeCombustible tipoCombustible;
  @Getter private double combustibleConsumidoPorKM;
  @Getter private ServicioGeoDds apiClient;

  public VehiculoParticular(TipoDeVehiculo tipoVehiculo, TipoDeCombustible tipoCombustible,
                            Ubicacion direccionInicio, Ubicacion direccionFin,
                            ServicioGeoDds apiClient, double combustible) {
    this.tipoVehiculo = tipoVehiculo;
    this.tipoCombustible = tipoCombustible;
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
