package domain.trayecto;

import domain.miembros.Miembro;
import domain.ubicaciones.Distancia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import static domain.ubicaciones.UnidadDeDistancia.MTS;

public class Trayecto {

  @Getter private final List<Tramo> tramos = new ArrayList<>();
  @Getter @Setter private Miembro owner;

  public void agregarTramo(Tramo tramo) {
    tramos.add(tramo);
  }

  public void agregarTramos(Tramo... tramos) {
    Collections.addAll(this.tramos, tramos);
  }

  public List<Miembro> miembros() {
    List<Miembro> ownerList = new ArrayList<>();
    ownerList.add(owner);
    return ownerList;
  }

  public Boolean ownerIs(Miembro miembro) {
    return this.getOwner().equals(miembro);
  }

  public Distancia distanciaTotal() {
    double distancia = this.getTramos().stream().mapToDouble(tramo -> tramo.distancia().valorEnMetros()).sum();
    return new Distancia(distancia, MTS);
  }

  public double combustibleTotalUtilizado() {
    return this.getTramos().stream().mapToDouble(Tramo::combustibleUtilizado).sum();
  }

}
