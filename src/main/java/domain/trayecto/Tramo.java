package domain.trayecto;

import domain.database.PersistenceEntity;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.trayecto.transporte.publico.Parada;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Tramo extends PersistenceEntity {

  @ManyToOne(cascade = CascadeType.ALL)
  private MedioDeTransporte medio;

  @Transient
  @Getter private Ubicacion ubicacionInicio;
  @Transient
  @Getter private Ubicacion ubicacionFin;

  public Tramo() {}

  public Tramo(MedioDeTransporte medio, Ubicacion ubicacionInicio, Ubicacion ubicacionFin) {
    this.medio = medio;
    this.ubicacionInicio = ubicacionInicio;
    this.ubicacionFin = ubicacionFin;
  }

  public Tramo(MedioDeTransporte medio, Parada paradaInicio, Parada paradaFin) {
    this.medio = medio;
    this.ubicacionInicio = paradaInicio.getUbicacionParada();
    this.ubicacionFin = paradaFin.getUbicacionParada();
  }

  public void validacionUbicaciones() {

  }

  public boolean admiteTrayectoCompartido() {
    return medio.admiteTrayectoCompartido();
  }

  public Distancia distancia() {
    return medio.distancia(ubicacionInicio, ubicacionFin);
  }

  public double combustibleUtilizado() {
    return medio.getCombustibleConsumidoPorKM() * (this.distancia().valorEnMetros() / 1000);
  }

}
