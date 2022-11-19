package domain.ubicaciones;

import java.io.IOException;

import domain.database.PersistenceEntity;
import domain.servicios.geodds.ServicioGeoDds;
import domain.servicios.geodds.excepciones.TimeoutException;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.sectores.Localidad;
import domain.ubicaciones.sectores.Municipio;
import domain.ubicaciones.sectores.Pais;
import domain.ubicaciones.sectores.Provincia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;

@Entity //(no puede ser Embeddable porque muchos usan 2 Ubicaciones en una misma clase)
public class Ubicacion extends PersistenceEntity {
  @Getter String calle;
  @Getter int altura;
  @Column(name = "nombre_pais")
  @Getter @Setter String nombrePais;
  @Column(name = "nombre_provincia")
  @Getter @Setter String nombreProvincia;
  @Column(name = "nombre_municipio")
  @Getter @Setter String nombreMunicipio;
  @Column(name = "nombre_localidad")
  @Getter @Setter String nombreLocalidad;
  @Transient
  ServicioGeoDds apiClient;
  @Transient
  Localidad localidad;


  public Ubicacion() {
    // Puede q esto cambie si hacemos q no sea Singleton
    apiClient = ServicioGeoDds.getInstancia();
  }

  @Deprecated
  public Ubicacion(String calle, int altura, String nombreLocalidad, ServicioGeoDds apiClient) {
    this.apiClient = ServicioGeoDds.getInstancia();
    this.calle = calle;
    this.altura = altura;
    //this.nombreLocalidad = nombreLocalidad;
  }

  public Ubicacion(String calle, int altura, String nombrePais,
                   String nombreProvincia,
                   String nombreMunicipio,
                   String nombreLocalidad) {
    this.apiClient = ServicioGeoDds.getInstancia();
    this.nombrePais = nombrePais;
    this.nombreProvincia = nombreProvincia;
    this.nombreMunicipio = nombreMunicipio;
    this.nombreLocalidad = nombreLocalidad;
    this.calle = calle;
    this.altura = altura;
  }

  // Tests
  public Ubicacion(String calle, int altura, ServicioGeoDds apiClient) {
    this.apiClient = apiClient;
    this.calle = calle;
    this.altura = altura;
  }

  // Tests 2
  public Ubicacion(String calle, int altura) {
    this.apiClient = ServicioGeoDds.getInstancia();
    this.calle = calle;
    this.altura = altura;
  }

  // Tests 3
  public Ubicacion(String calle, int altura, String nombrePais,
                   String nombreProvincia,
                   String nombreMunicipio,
                   String nombreLocalidad,
                   ServicioGeoDds apiClient) {
    this.apiClient = apiClient;
    this.nombrePais = nombrePais;
    this.nombreProvincia = nombreProvincia;
    this.nombreMunicipio = nombreMunicipio;
    this.nombreLocalidad = nombreLocalidad;
    this.calle = calle;
    this.altura = altura;
  }

  public Localidad getLocalidad() {
    if(localidad==null) {
      try {
        Pais pais = new Pais(nombrePais, apiClient);
        Provincia provincia = new Provincia(nombreProvincia, pais, apiClient);
        Municipio municipio = new Municipio(nombreMunicipio, provincia, apiClient);
        Localidad localidad = new Localidad(nombreLocalidad, municipio, apiClient);
        this.localidad = localidad;
        return localidad;
      } catch (IOException exception) {
        throw new TimeoutException("Timeout");
      }
    } else {
      return localidad;
    }
  }

  public Distancia calcularDistanciaA(Ubicacion otraUbicacion) throws IOException {
    return new Distancia(this.apiClient.distanciaEntreUbicaciones(this, otraUbicacion), MTS);
  }
}
