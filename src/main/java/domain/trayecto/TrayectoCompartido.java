package domain.trayecto;

import domain.organizaciones.miembros.Miembro;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class TrayectoCompartido extends Trayecto{

  public TrayectoCompartido() {}

  /*public TrayectoCompartido(List<Miembro> miembros, List<Tramo> tramos) {
    tramos.forEach(this::validacionTrayectoCompartido);
    this.miembros = miembros;
    this.tramos = tramos;
    agregarTrayCompAmiembros(miembros);
  }*/

  private void validacionTrayectoCompartido(Tramo tramo) {
    if(!tramo.admiteTrayectoCompartido()) {
      throw new RuntimeException("No se puede hacer un trayecto compartido que no sea "
          + "de tipo Servico Contratado o Vehiculo Particular en su totalidad");
    }
  }

  private void agregarTrayCompAmiembros(List<Miembro> miembrosAAgregar) {
    miembrosAAgregar
        .stream()
        .filter(miembro -> !miembro.equals(miembros.get(0))) //Por las dudas
        .forEach(miembro -> miembro.agregarTrayecto(this));
  }

  private void agregarTrayCompAmiembros(Miembro miembroAAgregar) {
    miembroAAgregar.agregarTrayecto(this);
  }

  @Override
  public void agregarTramo(Tramo tramo) {
    validacionTrayectoCompartido(tramo);
    this.tramos.add(tramo);
  }

  @Override
  public void agregarTramos(Tramo... tramos) {
    List<Tramo> listaDeTramos = Stream.of(tramos).collect(Collectors.toList());
    listaDeTramos.forEach(this::validacionTrayectoCompartido);
    Collections.addAll(this.tramos, tramos);
  }

  public void agregarTramos(List<Tramo> otrosTramos) {
    otrosTramos.forEach(this::validacionTrayectoCompartido);
    tramos.addAll(otrosTramos);
  }

  public void agregarAcompanante(Miembro acompanante) {
    miembros.add(acompanante);
    agregarTrayCompAmiembros(acompanante);
  }

  public void agregarAcompanantes(Miembro... acompanantes) {
    Collections.addAll(miembros, acompanantes);
    agregarTrayCompAmiembros(Stream.of(acompanantes).collect(Collectors.toList()));
  }

  public void agregarAcompanantes(List<Miembro> acompanantes) {
    miembros.addAll(acompanantes);
    agregarTrayCompAmiembros(acompanantes);
  }

}
