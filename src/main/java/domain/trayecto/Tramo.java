package domain.trayecto;

import domain.database.PersistenceEntity;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Tramo extends PersistenceEntity {

  @ManyToOne(cascade = CascadeType.ALL)
  private MedioDeTransporte medio;

  @Transient
  private Ubicacion ubicacionInicio;
  @Transient
  private Ubicacion ubicacionFin;

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
