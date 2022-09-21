package domain.trayecto.transporte;

import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.Ubicacion;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.IOException;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

@Entity(name = "medio_no_publico")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedioNoPublico extends MedioDeTransporte {

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
      e.printStackTrace();
      return new Distancia(-1.0, MTS);
    }
  }
}
