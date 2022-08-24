package domain.organizaciones;

import domain.organizaciones.consumos.tipos.*;
import lombok.Getter;

public class DatosActividades {

  @Getter TipoDeConsumo tipoDeConsumo;
  @Getter double valor;
  String periodicidad;
  String periodoImputacion;

  public DatosActividades (String tipo, String valor, String periodicidad, String periodoImputacion ){

    this.tipoDeConsumo = TipoDeConsumoFactory.instance().buildTipoDeConsumo(tipo);
    //this.valor = Integer.parseInt(valor);
    this.valor = Double.parseDouble(valor);
    this.periodicidad = periodicidad;
    this.periodoImputacion = periodoImputacion;

    }

    public double impactoHC(){
      if (this.periodicidad.equals("Anual")){
        return (valor * tipoDeConsumo.getFe().getValor())/12.0;
      } else {
        return valor * tipoDeConsumo.getFe().getValor();
      }
    }

}


