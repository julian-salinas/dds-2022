package domain.trayecto.transporte;

import java.util.List;

public class Linea {

  private String nombre;
  private List<Parada> paradas;
  private TipoTransportePublico tipo;

  public Linea(String nombre, List<Parada> paradas, TipoTransportePublico tipo) {
    this.nombre = nombre;
    this.paradas = paradas;
    this.tipo = tipo;
  }

  public boolean isTipo(TipoTransportePublico otroTipo) {
    return tipo.equals(otroTipo);
  }

  public boolean containsParadas(Parada parada) {
    return paradas.contains(parada);
  }

  public void agregarParada(Parada parada) {
    paradas.add(parada);
  }

}
