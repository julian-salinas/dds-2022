package domain.organizaciones;

import domain.organizaciones.consumos.tipos.*;
import lombok.Getter;

@Getter
public class DatosActividades {

  TipoDeConsumo tipoDeConsumo;
  int valor;
  String periodicidad;
  String periodoImputacion;

  public DatosActividades (String tipo, String valor, String periodicidad, String periodoImputacion ){

    this.tipoDeConsumo = TipoDeConsumoFactory.instance().buildTipoDeConsumo(tipo);
    this.valor = Integer.parseInt(valor);
    this.periodicidad = periodicidad;
    this.periodoImputacion = periodoImputacion;

    }
/*
   private TipoDeConsumo encontrarTipo(String tipo){

    switch (tipo){

      case "Gas Natural":
        return new GasNatural();

      case "Diesel/Gasoil":
        return new Diesel();

      case "Nafta":
        return new Nafta();

      case "Carbon":
        return new Carbon();

      case "Combustible Gasoil":
        return new CombustibleConsumidoGasoil();

      case "Combustible Nafta":
        return new CombustibleConsumidoNafta();

      case "Electricidad":
        return new Electricidad();

      case "Camion de carga":
      case "Utilitario liviano":
        return new Transporte();

      case "Distancia media":
        return new DistanciaRecorrida();
      }
     return null;
   }
*/

}


