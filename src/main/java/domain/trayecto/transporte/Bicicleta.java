package domain.trayecto.transporte;

public class Bicicleta implements MedioDeTransporte {
  private Direccion direccionInicio;
  private Direccion direccionFin;

  public Bicicleta(Direccion direccionInicio, Direccion direccionFin) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

}
