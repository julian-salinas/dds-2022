package dominio.trayecto.transporte;

public class TransportePublico extends MedioPublico {
  TipoTransportePublico tipo;
  Linea linea;

  public TransportePublico(TipoTransportePublico tipo, Linea linea, Parada paradaInicio, Parada paradaFin){
    this.tipo = tipo;
    this.linea = linea;
    this.paradaInicio = paradaInicio;
    this.paradaFin = paradaFin;
  }

}
