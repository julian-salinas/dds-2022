package domain.trayecto.transporte.nopublico;

import domain.servicios.geodds.excepciones.TimeoutException;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

@Entity(name = "vehiculo_particular")
public class VehiculoParticular extends MedioDeTransporte {
  @Enumerated(EnumType.STRING)
  private TipoDeVehiculo tipoVehiculo;
  @Enumerated(EnumType.STRING)
  private TipoDeCombustible tipoCombustible;
  @Getter @Setter private double combustibleConsumidoPorKM;
  //@Getter private String descripcion;

  public VehiculoParticular() {}

  public VehiculoParticular(TipoDeVehiculo tipoVehiculo, TipoDeCombustible tipoCombustible,
                            double combustible) {
    this.tipoVehiculo = tipoVehiculo;
    this.tipoCombustible = tipoCombustible;
    this.combustibleConsumidoPorKM = combustible;
    this.descripcion = this.toString();
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return true;
  }

  public Distancia distancia(Ubicacion ubicacionInicio, Ubicacion ubicacionFin) {
    try {
      return ubicacionInicio.calcularDistanciaA(ubicacionFin);
    } catch (IOException e) {
      throw new TimeoutException("Timeout");
    }
  }

  @Override
  public String toString() {
    // "Vehiculo Particular: " +
    return tipoVehiculo.toString().toUpperCase() +
        " de tipo de combustible ".toUpperCase() +
        tipoCombustible.toString().toUpperCase();
  }

}
