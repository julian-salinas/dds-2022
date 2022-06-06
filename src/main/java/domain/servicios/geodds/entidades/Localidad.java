package domain.servicios.geodds.entidades;

public class Localidad {
  public int id;
  public String nombre;
  public int codPostal;
  public Municipio municipio;

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public int getCodPostal() {
    return codPostal;
  }

  public Municipio getMunicipio() {
    return municipio;
  }
}
