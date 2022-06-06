package domain.servicios.geodds.entidades;

public class Municipio {
  public int id;
  public String nombre;
  public Provincia provincia;

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public Provincia getProvincia() {
    return provincia;
  }
}
