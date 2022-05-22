package domain.organizaciones;

import domain.miembros.Miembro;

import java.util.ArrayList;
import java.util.List;

public class Sector {
  List<Miembro> listaDeMiembros = new ArrayList<>();

  public void agregarMiembro(Miembro miembro) {
    listaDeMiembros.add(miembro);
  }

  public List<Miembro> getListaDeMiembros() {
    return listaDeMiembros;
  }
}


