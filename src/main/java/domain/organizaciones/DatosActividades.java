package domain.organizaciones;

import domain.organizaciones.consumos.tipos.TipoDeConsumo;

public class DatosActividades {

  TipoDeConsumo tipoDeConsumo;
  String valor;
  String periodicidad;
  String periodoImputacion;

  public DatosActividades (String tipo, String valor, String periodicidad, String periodoImputacion ){

    this.valor = valor;
    this.periodicidad = periodicidad;
    this.periodoImputacion = periodoImputacion;
    }
}
