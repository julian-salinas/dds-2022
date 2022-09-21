package domain.trayecto.transporte;

import domain.PersistenceEntity;
import domain.ubicaciones.distancia.Distancia;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name = "medio_de_transporte")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedioDeTransporte extends PersistenceEntity {
  public abstract Distancia distancia();
  public abstract Boolean admiteTrayectoCompartido();
  public abstract double getCombustibleConsumidoPorKM();
}
