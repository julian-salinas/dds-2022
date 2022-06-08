package domain.trayecto;

import domain.miembros.Miembro;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

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

  public int distanciaTotal() {
    return tramos.stream().mapToInt(Tramo::distancia).sum();
  }

}
