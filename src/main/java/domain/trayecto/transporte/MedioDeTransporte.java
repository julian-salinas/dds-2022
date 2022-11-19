package domain.trayecto.transporte;

import domain.database.PersistenceEntity;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;

import javax.persistence.*;
import java.io.IOException;

@Entity//(name = "medio_de_transporte")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedioDeTransporte extends PersistenceEntity
{
  @Getter
  public String descripcion;
  public abstract Distancia distancia(Ubicacion ubicacionInicio, Ubicacion ubicacionFin);
  public abstract Boolean admiteTrayectoCompartido();
  public abstract double getCombustibleConsumidoPorKM();
  public abstract String toString();

}
