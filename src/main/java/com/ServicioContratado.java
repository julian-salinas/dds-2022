package com;

public class ServicioContratado extends MedioNoPublico{
  TipoServicioContratado tipo;

  public ServicioContratado(TipoServicioContratado tipo, Direccion direccionInicio, Direccion direccionFin){
    this.tipo = tipo;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

}
