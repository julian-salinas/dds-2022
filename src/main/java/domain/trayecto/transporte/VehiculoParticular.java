package domain.trayecto.transporte;

import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.io.IOException;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

@Entity(name = "vehiculo_particular")
public class VehiculoParticular extends MedioDeTransporte {
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
