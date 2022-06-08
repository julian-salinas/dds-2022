package domain.ubicaciones;

import lombok.Getter;
import domain.servicios.geodds.entidades.Localidad;

@Getter
public class Ubicacion {
  String calle;
  int altura;
  Localidad localidad;
}
