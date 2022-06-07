package domain.trayecto.transporte;

import domain.servicios.geodds.ServicioGeoDds;
import domain.servicios.geodds.entidades.Distancia;
import domain.ubicaciones.Ubicacion;
import java.io.IOException;

public class Pie implements MedioDeTransporte {
  private Ubicacion direccionInicio;
  private Ubicacion direccionFin;

  public Pie(Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

  public int getDistancia() {
    ServicioGeoDds api = ServicioGeoDds.getInstancia();
    Distancia distancia;
    try {
      distancia = api.distanciaEntreUbicaciones(
          direccionInicio.getLocalidad(),
          direccionInicio.getCalle(),
          direccionInicio.getAltura(),
          direccionFin.getLocalidad(),
          direccionFin.getCalle(),
          direccionFin.getAltura()
      );
      // Podemos cambiar a que devuelva Double, o incluso Distancia
      return (int) distancia.valor;
    } catch (IOException e) {
      // bruh
      return -1;
    }
  }

}
