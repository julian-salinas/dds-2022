package domain.trayecto.transporte;

import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.Distancia;
import domain.ubicaciones.Ubicacion;

import java.io.IOException;

import static domain.ubicaciones.UnidadDeDistancia.MTS;

public abstract class MedioNoPublico implements MedioDeTransporte {

  abstract ServicioGeoDds getApiClient();
  abstract Ubicacion getDireccionInicio();
  abstract Ubicacion getDireccionFin();

  @Override
  public Boolean admiteTrayectoCompartido() {
    return false;
  }

  public Distancia getDistancia() {
    //ServicioGeoDds apiClient = ServicioGeoDds.getInstancia();

    try {
      double distanciaAPI = this.getApiClient().distanciaEntreUbicaciones(
          this.getDireccionInicio(),
          this.getDireccionFin()
      );
      return new Distancia(distanciaAPI, MTS);
    } catch (IOException e) {
      // bruh
      return new Distancia(-1.0, MTS);
    }
  }
}
