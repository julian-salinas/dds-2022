package domain.trayecto.transporte;
import domain.ubicaciones.Ubicacion;

public class Pie implements MedioDeTransporte {
  private Ubicacion direccionInicio;
  private Ubicacion direccionFin;

  public Pie(Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

  public int getDistancia() {
    // TODO
    return 0;
  }

}
