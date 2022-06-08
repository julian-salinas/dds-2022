package domain.trayecto.transporte;

import domain.servicios.geodds.ServicioGeoDds;
import domain.servicios.geodds.entidades.Distancia;
import domain.ubicaciones.Ubicacion;

import java.io.IOException;

public abstract class MedioNoPublico implements MedioDeTransporte {

  abstract Ubicacion getDireccionInicio();
  abstract Ubicacion getDireccionFin();

  public int getDistancia() {
    ServicioGeoDds api = ServicioGeoDds.getInstancia();
    Distancia distancia;
    try {
      distancia = api.distanciaEntreUbicaciones(
          this.getDireccionInicio().getLocalidad().getId(),
          this.getDireccionInicio().getCalle(),
          this.getDireccionInicio().getAltura(),
          this.getDireccionFin().getLocalidad().getId(),
          this.getDireccionFin().getCalle(),
          this.getDireccionFin().getAltura()
      );
      // Podemos cambiar a que devuelva Double, o incluso Distancia
      return (int) distancia.valor;
    } catch (IOException e) {
      // bruh
      return -1;
    }
  }
}
