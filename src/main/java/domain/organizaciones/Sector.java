package domain.organizaciones;

import domain.miembros.Miembro;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Sector {
  @Setter @Getter private Organizacion orgAlaQuePertenezco;
  private final List<Miembro> miembros = new ArrayList<>();
  private final List<Miembro> miembrosParaAceptar = new ArrayList<>();

  public boolean containsMiembro(Miembro miembro) {
    return miembros.contains(miembro);
  }

  public void agregarMiembro(Miembro miembro) {
    miembro.setSectorDondeTrabaja(this);
    miembros.add(miembro);
  }

  public boolean containsMiembroParaAceptar(Miembro miembro) {
    return miembrosParaAceptar.contains(miembro);
  }

  public void agregarMiembroParaAceptar(Miembro miembro) {
    miembrosParaAceptar.add(miembro);
  }

  public void sacarMiembroParaAceptar(Miembro miembro) {
    miembrosParaAceptar.remove(miembro);
  }

}


