package domain.trayecto;

import domain.PersistenceEntity;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.ubicaciones.distancia.Distancia;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Tramo extends PersistenceEntity {

  @Transient
  private MedioDeTransporte medio;

  public Tramo() {}

  public Tramo(MedioDeTransporte medio) {
    this.medio = medio;
  }

  public Distancia distancia() {
    return medio.distancia();
  }

  public boolean admiteTrayectoCompartido() {
    return medio.admiteTrayectoCompartido();
  }

  public double combustibleUtilizado() {
    return medio.getCombustibleConsumidoPorKM() * (this.distancia().valorEnMetros() / 1000);
  }

}
