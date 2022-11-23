package domain.organizaciones.datos.actividades.tipos;

import lombok.Getter;

@Getter
public class DataHelper {
  Double porcentaje;
  String nombre;

  public DataHelper (Double porcentaje, String nombre){
    this.porcentaje = porcentaje;
    this.nombre = nombre;
  };

}
