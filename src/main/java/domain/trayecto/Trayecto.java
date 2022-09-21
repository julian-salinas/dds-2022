package domain.trayecto;

import domain.PersistenceEntity;
import domain.organizaciones.miembros.Miembro;
import domain.ubicaciones.distancia.Distancia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminador")
public class Trayecto extends PersistenceEntity {

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "trayecto_id")
  @Getter public List<Tramo> tramos = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "owner_id")
  @Getter @Setter public Miembro owner;

  public void agregarTramo(Tramo tramo) {
    tramos.add(tramo);
  }

  public void agregarTramos(Tramo... tramos) {
    Collections.addAll(this.tramos, tramos);
  }

  public List<Miembro> miembros() {
    List<Miembro> ownerList = new ArrayList<>();
    ownerList.add(owner);
    return ownerList;
  }

  public Boolean ownerIs(Miembro miembro) {
    return this.getOwner().equals(miembro);
  }

  public Distancia distanciaTotal() {
    double distancia = this.getTramos().stream().mapToDouble(tramo -> tramo.distancia().valorEnMetros()).sum();
    return new Distancia(distancia, MTS);
  }

  public double combustibleTotalUtilizado() {
    return this.getTramos().stream().mapToDouble(Tramo::combustibleUtilizado).sum();
  }

}
