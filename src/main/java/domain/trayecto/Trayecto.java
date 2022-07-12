package domain.trayecto;

import domain.miembros.Miembro;
import java.util.ArrayList;
import java.util.List;

import domain.ubicaciones.Distancia;
import lombok.Getter;
import lombok.Setter;

import static domain.ubicaciones.UnidadDeDistancia.MTS;

public class Trayecto {

  @Getter private final List<Tramo> tramos = new ArrayList<>();
  @Getter @Setter private Miembro owner;

  public void agregarTramo(Tramo tramo) {
    tramos.add(tramo);
  }

  public List<Miembro> miembros() {
    List<Miembro> ownerList = new ArrayList<>();
    ownerList.add(owner);
    return ownerList;
  }

  public Distancia distanciaTotal() {
    double distancia = tramos.stream().mapToDouble(tramo -> tramo.distancia().valorEnMetros()).sum();
    return new Distancia(distancia, MTS);
  }

}
