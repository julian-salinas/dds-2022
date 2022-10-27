package domain.trayecto.transporte;

import domain.database.PersistenceEntity;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;

import javax.persistence.*;

@Entity//(name = "medio_de_transporte")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedioDeTransporte extends PersistenceEntity
{
  public abstract Distancia distancia(Ubicacion ubicacionInicio, Ubicacion ubicacionFin);
  public abstract Boolean admiteTrayectoCompartido();
  public abstract double getCombustibleConsumidoPorKM();
  public abstract String toString();

  public String getDescripcion() {
    return this.toString();
  }
}
