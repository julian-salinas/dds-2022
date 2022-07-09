package domain.trayecto.transporte;

import java.util.List;

import domain.ubicaciones.Distancia;
import domain.ubicaciones.UnidadDeDistancia;
import lombok.Getter;

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

  private boolean isInRange(Parada parada, int indiceInicial, int indiceFinal) {
    return paradas.indexOf(parada) >= indiceInicial
        && paradas.indexOf(parada) < indiceFinal;
  }

  public Distancia distanciaEntreParadas(Parada paradaInicio, Parada paradaFin) {
    int indiceInicial = paradas.indexOf(paradaInicio);
    int indiceFinal = paradas.indexOf(paradaFin);

    double distanciaEnMetros =  paradas
        .stream()
        .filter(parada -> isInRange(parada, indiceInicial, indiceFinal))
        .mapToDouble(parada -> parada.getDistAproximaParada().valorEnMetros())
        .sum();

    return new Distancia(distanciaEnMetros, UnidadDeDistancia.MTS);
  }

}
