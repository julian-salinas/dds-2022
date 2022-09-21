package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Entity(name = "servicio_contratado")
public class ServicioContratado extends MedioNoPublico {
  @Transient
  @Getter private Ubicacion direccionInicio;
  @Transient
  @Getter private Ubicacion direccionFin;
  @Embedded
  private TipoServicioContratado tipo;
  @Getter @Setter private double combustibleConsumidoPorKM; // No se que deberia poner en los que desconocemos

  public ServicioContratado() {}

  public ServicioContratado(TipoServicioContratado tipo,
                            Ubicacion direccionInicio,
                            Ubicacion direccionFin,
                            double combustible) {
    this.tipo = tipo;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
    this.combustibleConsumidoPorKM = combustible;
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return true;
  }
}
