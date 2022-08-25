package domain.trayecto.transporte;

import domain.ubicaciones.Distancia;
import domain.ubicaciones.Ubicacion;
import java.io.IOException;

import static domain.ubicaciones.UnidadDeDistancia.MTS;

public abstract class MedioNoPublico implements MedioDeTransporte {

  abstract Ubicacion getDireccionInicio();
  abstract Ubicacion getDireccionFin();

  @Override
  public Boolean admiteTrayectoCompartido() {
    return false;
  }

  public Distancia distancia() {
    try {
      return this.getDireccionInicio().calcularDistanciaA(this.getDireccionFin());
    } catch (IOException e) {
      // bruh
      return new Distancia(-1.0, MTS);
    }
  }
}
