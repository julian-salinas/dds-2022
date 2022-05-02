package com;

public class VehiculoParticular extends MedioNoPublico{
  TipoDeVehiculo tipoVehiculo;
  TipoDeCombustible tipoCombustible;

  public VehiculoParticular(TipoDeVehiculo tipoVehiculo, TipoDeCombustible tipoCombustible, Direccion direccionInicio, Direccion direccionFin){
    this.tipoVehiculo = tipoVehiculo;
    this.tipoCombustible = tipoCombustible;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

}
