package domain.organizaciones.sectores;

import domain.PersistenceEntity;
import domain.trayecto.Trayecto;
import domain.organizaciones.Organizacion;
import domain.organizaciones.miembros.Miembro;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Sector extends PersistenceEntity {
  //@Setter @Getter private Organizacion orgAlaQuePertenezco;

  @OneToMany @JoinColumn(name = "sector_id")
  private final List<Miembro> miembros = new ArrayList<>();

  @OneToMany @JoinColumn(name = "sector_id")
  private final List<Miembro> miembrosParaAceptar = new ArrayList<>(); // --> Tal vez haya q cambiarlo para persistir

  public boolean containsMiembro(Miembro miembro) {
    return miembros.contains(miembro);
  }

  public void agregarMiembro(Miembro miembro) {
    //miembro.setSectorDondeTrabaja(this);
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


