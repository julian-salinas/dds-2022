package domain.trayecto;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DefinirTipo {
  List<TiposDeConsumo> tipos = new List<TiposDeConsumo>();

  public DefinirTipo() {}

  public void addTipo(TipoDeConsumo){
    tipos.add(TipoDeConsumo);
  }

  public TipoDeConsumo encontrarTipo(String tipo){
    TipoDeConsumo tipoDeConsumo = tipos.filter(x -> tipo.equals(x.getNombre() )).getFirst();
    return tipoDeConsumo;
  }


}
z