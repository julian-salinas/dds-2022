package dominio.trayecto;

import java.util.ArrayList;
import java.util.List;

public class Trayecto {
  List<Tramo> tramos = new ArrayList<>();

  public void agregarTramo(Tramo tramo){
    tramos.add(tramo);
  }

}
