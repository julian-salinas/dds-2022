package domain.trayecto.transporte;

import domain.ubicaciones.Distancia;
import domain.ubicaciones.UnidadDeDistancia;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

// Ida distinta de vuelta:
//  - Las paradas estan seteadas de forma circular (despues de la ultima esta la primera)
//  - Se circula solo en un sentido (para adelante)

// Ida igual a vuelta:
//  - Se circula en ambos sentidos

public class Linea {

  private String nombre;
  @Getter private List<Parada> paradas;
  private TipoTransportePublico tipo;
  @Setter private boolean idaIgualAVuelta = false;

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

    //boolean esParaAtras = indiceFinal < indiceInicial;

    if(!idaIgualAVuelta) {
      boolean tieneQueCruzar = indiceFinal < indiceInicial;
      if(!tieneQueCruzar){
        double distanciaEnMetros = paradas
            .stream()
            .filter(parada -> isInRange(parada, indiceInicial, indiceFinal))
            .mapToDouble(parada -> parada.getDistAproximaParada().valorEnMetros())
            .sum();

        return new Distancia(distanciaEnMetros, UnidadDeDistancia.MTS);
      } else {
        double distanciaEnMetros = paradas
            .stream()
            .filter(parada -> isInRange(parada, indiceInicial, paradas.size()))
            .mapToDouble(parada -> parada.getDistAproximaParada().valorEnMetros())
            .sum()
            +
            paradas
                .stream()
                .filter(parada -> isInRange(parada, 0, indiceFinal))
                .mapToDouble(parada -> parada.getDistAproximaParada().valorEnMetros())
                .sum();
        return new Distancia(distanciaEnMetros, UnidadDeDistancia.MTS);
      }
    } else {
      boolean esParaAtras = indiceFinal < indiceInicial;
      if(!esParaAtras) {
        double distanciaEnMetros = paradas
            .stream()
            .filter(parada -> isInRange(parada, indiceInicial, indiceFinal))
            .mapToDouble(parada -> parada.getDistAproximaParada().valorEnMetros())
            .sum();

        return new Distancia(distanciaEnMetros, UnidadDeDistancia.MTS);
      } else {
        double distanciaEnMetros = paradas
            .stream()
            .filter(parada -> isInRange(parada, indiceFinal, indiceInicial))
            .mapToDouble(parada -> parada.getDistAproximaParada().valorEnMetros())
            .sum();

        return new Distancia(distanciaEnMetros, UnidadDeDistancia.MTS);
      }
      //return new Distancia(0.0, UnidadDeDistancia.MTS);
    }
  }

}
