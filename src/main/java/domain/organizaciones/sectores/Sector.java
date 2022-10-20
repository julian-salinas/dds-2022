package domain.organizaciones.sectores;

import domain.database.PersistenceEntity;
import domain.trayecto.Trayecto;
import domain.organizaciones.miembros.Miembro;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;


@Entity
public class Sector extends PersistenceEntity {

  /*String nombre;
  String descripcion;*/

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "sector_id")
  private final List<Miembro> miembros = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "sector_posible_id")
  private final List<Miembro> miembrosParaAceptar = new ArrayList<>();

  public boolean containsMiembro(Miembro miembro) {
    return miembros.contains(miembro);
  }

  public void agregarMiembro(Miembro miembro) {
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


