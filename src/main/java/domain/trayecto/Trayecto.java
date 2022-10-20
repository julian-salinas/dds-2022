package domain.trayecto;

import domain.database.PersistenceEntity;
import domain.organizaciones.miembros.Miembro;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.*;
import lombok.Getter;

import static domain.ubicaciones.distancia.UnidadDistancia.MTS;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminador")
public class Trayecto extends PersistenceEntity {

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "trayecto_id")
  @Getter public List<Tramo> tramos = new ArrayList<>();

  @ManyToMany(mappedBy = "trayectos")
  @Getter public List<Miembro> miembros = new ArrayList<>();

  public void agregarTramo(Tramo tramo) {
    tramos.add(tramo);
  }

  public void agregarTramos(Tramo... tramos) {
    Collections.addAll(this.tramos, tramos);
  }

  public void setOwner(Miembro owner) {
    List<Miembro> aux = new ArrayList<>();
    aux.add(owner);
    aux.addAll(miembros);
    miembros = aux;
  }

  public Miembro getOwner() {
    return miembros.get(0);
  }

  public Boolean ownerIs(Miembro miembro) {
    return this.getOwner().equals(miembro);
  }

  public Ubicacion getUbicacionInicio() {
    return tramos.get(0).getUbicacionInicio();
  }

  public Ubicacion getUbicacionFin() {
    return tramos.get(tramos.size()-1).getUbicacionFin();
  }

  public Distancia distanciaTotal() {
    double distancia = tramos
        .stream()
        .mapToDouble(tramo -> tramo.distancia().valorEnMetros())
        .sum();
    return new Distancia(distancia, MTS);
  }

  public double combustibleTotalUtilizado() {
    return this.getTramos().stream().mapToDouble(Tramo::combustibleUtilizado).sum();
  }

}
