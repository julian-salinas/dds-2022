package domain.trayecto.transporte;

public class VehiculoParticular implements MedioDeTransporte {

  private Direccion direccionInicio;
  private Direccion direccionFin;
  private final TipoDeVehiculo tipoVehiculo;
  private TipoDeCombustible tipoCombustible;

  public VehiculoParticular(TipoDeVehiculo tipoVehiculo, TipoDeCombustible tipoCombustible,
                            Direccion direccionInicio, Direccion direccionFin) {
    this.tipoVehiculo = tipoVehiculo;
    this.tipoCombustible = tipoCombustible;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

}
