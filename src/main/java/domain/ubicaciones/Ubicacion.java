package domain.ubicaciones;

import java.io.IOException;

import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.sectores.Localidad;
import lombok.Getter;

import javax.persistence.Transient;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;


public class Ubicacion {
  @Getter String calle;
  @Getter int altura;
  @Transient // a cambiar
  @Getter Localidad localidad;
  @Transient // va a quedar como Transient (no tiene sentido guardarlo en la db)
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
