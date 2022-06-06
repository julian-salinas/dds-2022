package domain.trayecto.transporte;
import domain.ubicaciones.Ubicacion;

public class Bicicleta implements MedioDeTransporte {
  private Ubicacion direccionInicio;
  private Ubicacion direccionFin;

  public Bicicleta(Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

  public int getDistancia() {
    // TODO
    return 0;
  }

}
