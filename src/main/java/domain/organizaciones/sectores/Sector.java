package domain.organizaciones.sectores;

import domain.trayecto.Trayecto;
import domain.organizaciones.Organizacion;
import domain.organizaciones.miembros.Miembro;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

  public double combustibleConsumidoTransporteMiembros(){
    Set<Trayecto> trayectos = miembros
        .stream()
        .flatMap(miembro -> miembro.getTrayectos().stream())
        .collect(Collectors.toSet());
    return trayectos.stream().mapToDouble(Trayecto::combustibleTotalUtilizado).sum();
  }

}


