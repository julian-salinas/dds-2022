package dominio.trayecto.transporte;

import java.util.ArrayList;
import java.util.List;

public class Linea {
  String nombre;
  List<Parada> paradas = new ArrayList<>();
  TipoTransportePublico tipo;

  public void Linea(String nombre, List<Parada> paradas, TipoTransportePublico tipo){
    this.nombre = nombre;
    this.paradas = paradas;
    this.tipo = tipo;
  }

  public void agregarParada(Parada parada){
    paradas.add(parada);
  }

}
