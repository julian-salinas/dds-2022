package domain.trayecto.transporte;

public class Pie implements MedioDeTransporte {
  private Direccion direccionInicio;
  private Direccion direccionFin;

  public Pie(Direccion direccionInicio, Direccion direccionFin) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

  public int getDistancia() {
    // TODO
    return 0;
  }

}
