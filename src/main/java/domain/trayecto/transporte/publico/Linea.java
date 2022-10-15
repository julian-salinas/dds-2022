package domain.trayecto.transporte.publico;

import domain.database.PersistenceEntity;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.distancia.UnidadDistancia;
import java.util.List;
import lombok.Getter;

import javax.persistence.*;

// Unidireccional:
//  - Las paradas estan seteadas de forma circular (despues de la ultima esta la primera)
//  - Se circula solo en un sentido (para adelante)

// Bidireccional:
//  - Se circula en ambos sentidos

@Entity
public class Linea extends PersistenceEntity {

  private String nombre;
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinTable(name = "linea_x_parada")
  @Getter private List<Parada> paradas;
  @Enumerated(EnumType.STRING)
  private TipoTransportePublico tipo;
  private boolean bidireccional;// = false;

  public Linea() {}

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

  public void setUnidireccional() {
    bidireccional = false;
  }

  public void setBidireccional() {
    bidireccional = true;
  }

  private boolean isInRange(Parada parada, int indiceInicial, int indiceFinal) {
    return paradas.indexOf(parada) >= indiceInicial
        && paradas.indexOf(parada) < indiceFinal;
  }

  public Distancia distanciaEntreParadas(Parada paradaInicio, Parada paradaFin) {
    int indiceInicial = paradas.indexOf(paradaInicio);
    int indiceFinal = paradas.indexOf(paradaFin);
    double distanciaEnMetros;

    if(!bidireccional) {
      boolean tieneQueCruzar = indiceFinal < indiceInicial;
      if(!tieneQueCruzar){
        distanciaEnMetros = distSentidoHaciaAdelante(indiceInicial, indiceFinal);
      } else {
        distanciaEnMetros = distSentidoHaciaAdelanteCruzando(indiceInicial, indiceFinal);
      }
    } else {
      boolean esParaAtras = indiceFinal < indiceInicial;
      if(!esParaAtras) {
        distanciaEnMetros = distSentidoHaciaAdelante(indiceInicial, indiceFinal);
      } else {
        distanciaEnMetros = distSentidoHaciaAtras(indiceInicial, indiceFinal);
      }
    }
    return new Distancia(distanciaEnMetros, UnidadDistancia.MTS);
  }

  private double distEntreIndices(int indiceInicial, int indiceFinal) {
    return paradas
        .stream()
        .filter(parada -> isInRange(parada, indiceInicial, indiceFinal))
        .mapToDouble(parada -> parada.getDistAproximaParada().valorEnMetros())
        .sum();
  }

  private double distSentidoHaciaAdelante(int indiceInicial, int indiceFinal) {
    return distEntreIndices(indiceInicial, indiceFinal);
  }

  private double distSentidoHaciaAtras(int indiceInicial, int indiceFinal) {
    return distEntreIndices(indiceFinal, indiceInicial);
  }

  private double distSentidoHaciaAdelanteCruzando(int indiceInicial, int indiceFinal) {
    return distSentidoHaciaAdelante(indiceInicial, paradas.size())
        + distSentidoHaciaAdelante(0, indiceFinal);
  }

}
