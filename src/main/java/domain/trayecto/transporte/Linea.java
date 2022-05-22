package domain.trayecto.transporte;

import java.util.ArrayList;
import java.util.List;

public class Linea {

  String nombre;
  List<Parada> paradas;
  TipoTransportePublico tipo;

  public Linea(String nombre, List<Parada> paradas, TipoTransportePublico tipo) {
    this.nombre = nombre;
    this.paradas = paradas;
    this.tipo = tipo;
  }

  public List<Parada> getParadas() {
    return paradas;
  }

  public TipoTransportePublico getTipo() {
    return tipo;
  }

  public void agregarParada(Parada parada) {
    paradas.add(parada);
  }

}
