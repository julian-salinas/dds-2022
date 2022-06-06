package domain.servicios.geodds.entidades;

public class Provincia {
  public int id;
  public String nombre;
  public Pais pais;

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public Pais getPais() {
    return pais;
  }

  public Provincia getItself() {
    return this;
  }
}
