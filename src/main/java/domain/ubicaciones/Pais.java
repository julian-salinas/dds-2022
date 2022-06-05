package domain.ubicaciones;

import domain.ubicaciones.client.GeoDdSAPIClient;

public class Pais {
  private Integer idPais;
  private String nombrePais;
  private GeoDdSAPIClient apiClient;

  public Pais(int idPais, String nombrePais) {
    this.idPais = idPais;
    this.nombrePais = nombrePais;
  }

  public Pais(int idPais) {
    this.idPais = idPais;
    this.nombrePais = this.apiClient.getNombrePais(idPais);
  }

  public Pais(String nombrePais) {
    this.idPais = this.apiClient.getIdPais(nombrePais);
    this.nombrePais = nombrePais;
  }

}
