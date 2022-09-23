package domain.ubicaciones;

import java.io.IOException;

import domain.PersistenceEntity;
import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.sectores.Localidad;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Transient;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

@Entity //(no puede ser Embeddable porque muchos usan 2 Ubicaciones en una misma clase)
public class Ubicacion extends PersistenceEntity {
  @Getter String calle;
  @Getter int altura;
  @Column(name = "nombre_localidad")
  @Getter String nombreLocalidad;
  @Transient // va a quedar como Transient (no tiene sentido guardarlo en la db)
  ServicioGeoDds apiClient;

  public Ubicacion() {}
  public Ubicacion(String calle, int altura, String nombreLocalidad, ServicioGeoDds apiClient) {
    this.apiClient = apiClient;
    this.calle = calle;
    this.altura = altura;
    this.nombreLocalidad = nombreLocalidad;
  }

  // Hacer esto para Localidad, Municipio, etc. O tal vez no.
  public Localidad getLocalidad() {
    try {
      return new Localidad(nombreLocalidad, apiClient);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Distancia calcularDistanciaA(Ubicacion otraUbicacion) throws IOException {
    return new Distancia(this.apiClient.distanciaEntreUbicaciones(this, otraUbicacion), MTS);
  }
}
