package domain.ubicaciones;

import java.io.IOException;

import domain.servicios.geodds.ServicioGeoDds;

import static domain.ubicaciones.UnidadDeDistancia.KM;

public class Ubicacion {
  String calle;
  int altura;
  Localidad localidad;
  ServicioGeoDds apiClient;

  public Ubicacion(String calle, int altura, String nombreLocalidad) throws IOException, RuntimeException {
    this.apiClient = ServicioGeoDds.getInstancia();
    this.calle = calle;
    this.altura = altura;
    this.localidad = new Localidad(nombreLocalidad);
  }

  public String getCalle() {
    return calle;
  }

  public int getAltura() {
    return altura;
  }

  public Localidad getLocalidad() {
    return localidad;
  }

  public Distancia calcularDistanciaA(Ubicacion otraUbicacion) throws IOException {
    return new Distancia(this.apiClient.distanciaEntreUbicaciones(this, otraUbicacion), KM);
  }
}
