package domain.ubicaciones;

import java.io.IOException;
import lombok.Getter;

@Getter
public class Ubicacion {
  String calle;
  int altura;
  Localidad localidad;

  public Ubicacion(String calle, int altura, String nombreLocalidad) throws IOException, RuntimeException {
    this.calle = calle;
    this.altura = altura;
    this.localidad = new Localidad(nombreLocalidad);
  }
}
