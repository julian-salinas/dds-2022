package domain.trayecto;

import java.util.ArrayList;
import java.util.List;

public class Trayecto {

  private final List<Tramo> tramos = new ArrayList<>();

  public void agregarTramo(Tramo tramo) {
    tramos.add(tramo);
  }

  public int distanciaTotal(){
    return tramos.stream().mapToInt(Tramo::distancia).sum();
  }

}
