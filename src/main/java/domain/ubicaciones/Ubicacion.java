package domain.ubicaciones;

import java.io.IOException;

import domain.servicios.geodds.ServicioGeoDds;
import lombok.Getter;

import static domain.ubicaciones.UnidadDeDistancia.KM;
import static domain.ubicaciones.UnidadDeDistancia.MTS;

public class Ubicacion {
  @Getter String calle;
  @Getter int altura;
  @Getter Localidad localidad;
  ServicioGeoDds apiClient;

  public Ubicacion(String calle, int altura, String nombreLocalidad, ServicioGeoDds apiClient)
      throws IOException, RuntimeException {
    this.apiClient = apiClient;
    this.calle = calle;
    this.altura = altura;
    this.localidad = new Localidad(nombreLocalidad, apiClient);
  }

  public Distancia calcularDistanciaA(Ubicacion otraUbicacion) throws IOException {
    return new Distancia(this.apiClient.distanciaEntreUbicaciones(this, otraUbicacion), MTS);
  }
}
