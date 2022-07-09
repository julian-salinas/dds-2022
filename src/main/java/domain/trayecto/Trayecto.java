package domain.trayecto;

import domain.miembros.Miembro;
import java.util.ArrayList;
import java.util.List;

import domain.ubicaciones.Distancia;
import lombok.Setter;

import static domain.ubicaciones.UnidadDeDistancia.MTS;

public class Trayecto {

  private final List<Tramo> tramos = new ArrayList<>();
  @Setter private Miembro miembroQueMeCargo;

  public void agregarTramo(Tramo tramo) {
    tramos.add(tramo);
  }

  public List<Miembro> getMiembrosQueMeCargaron() {
    List<Miembro> miembroQueMeCargoList = new ArrayList<>();
    miembroQueMeCargoList.add(miembroQueMeCargo);
    return miembroQueMeCargoList;
  }

  public Distancia distanciaTotal() {
    double distancia = tramos.stream().mapToDouble(tramo -> tramo.distancia().valorEnMetros()).sum();
    return new Distancia(distancia, MTS);
  }

}
