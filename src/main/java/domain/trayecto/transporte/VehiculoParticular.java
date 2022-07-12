package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import lombok.Getter;

public class VehiculoParticular extends MedioNoPublico {

  @Getter private Ubicacion direccionInicio;
  @Getter private Ubicacion direccionFin;
  private final TipoDeVehiculo tipoVehiculo;
  private TipoDeCombustible tipoCombustible;
  @Getter private double combustibleConsumidoPorKM = 0;

  public VehiculoParticular(TipoDeVehiculo tipoVehiculo, TipoDeCombustible tipoCombustible,
                            Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.tipoVehiculo = tipoVehiculo;
    this.tipoCombustible = tipoCombustible;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return true;
  }

}
