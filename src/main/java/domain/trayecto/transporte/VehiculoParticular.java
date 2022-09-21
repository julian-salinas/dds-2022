package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Entity(name = "vehiculo_particular")
public class VehiculoParticular extends MedioNoPublico {
  @Transient
  @Getter private Ubicacion direccionInicio;
  @Transient
  @Getter private Ubicacion direccionFin;
  @Enumerated(EnumType.STRING)
  private TipoDeVehiculo tipoVehiculo;
  @Enumerated(EnumType.STRING)
  private TipoDeCombustible tipoCombustible;
  @Getter @Setter private double combustibleConsumidoPorKM;

  public VehiculoParticular() {}

  public VehiculoParticular(TipoDeVehiculo tipoVehiculo, TipoDeCombustible tipoCombustible,
                            Ubicacion direccionInicio, Ubicacion direccionFin,
                            double combustible) {
    this.tipoVehiculo = tipoVehiculo;
    this.tipoCombustible = tipoCombustible;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
    this.combustibleConsumidoPorKM = combustible;
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return true;
  }

}
