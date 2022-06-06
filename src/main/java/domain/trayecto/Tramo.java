package domain.trayecto;

import domain.trayecto.transporte.MedioDeTransporte;

public class Tramo {

  private MedioDeTransporte medio;

  public Tramo(MedioDeTransporte medio) {
    this.medio = medio;
  }

  public int distancia() {
    return medio.getDistancia();
  }

}
