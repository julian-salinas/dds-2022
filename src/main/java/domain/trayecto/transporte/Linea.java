package domain.trayecto.transporte;

import lombok.Getter;

import java.util.List;

public class Linea {

  private String nombre;
  @Getter private List<Parada> paradas; // [ini,...,fin] --> fin->ini (circular)
  private TipoTransportePublico tipo;

  public Linea(String nombre, List<Parada> paradas, TipoTransportePublico tipo) {
    this.nombre = nombre;
    this.paradas = paradas;
    this.tipo = tipo;
  }

  public boolean isTipo(TipoTransportePublico otroTipo) {
    return tipo.equals(otroTipo);
  }

  public boolean containsParada(Parada parada) {
    return paradas.contains(parada);
  }

  public void agregarParada(Parada parada) {
    paradas.add(parada);
  }

}
