package domain.trayecto;

import domain.trayecto.transporte.MedioDeTransporte;
import domain.ubicaciones.Distancia;

public class Tramo {

  private MedioDeTransporte medio;

  public Tramo(MedioDeTransporte medio) {
    this.medio = medio;
  }

  public Distancia distancia() {
    return medio.getDistancia();
  }

  public Boolean admiteTrayectoCompartido() {
    return medio.admiteTrayectoCompartido();
  }

  public double combustibleUtilizado(){
    return medio.getCombustibleConsumidoPorKM() * this.distancia().valorEnMetros() * 1000;
  }

}
