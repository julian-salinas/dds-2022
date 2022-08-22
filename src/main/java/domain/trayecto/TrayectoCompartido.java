package domain.trayecto;

import domain.miembros.Miembro;
import domain.ubicaciones.Distancia;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static domain.ubicaciones.UnidadDeDistancia.MTS;

public class TrayectoCompartido extends Trayecto{

  private List<Miembro> miembros = new ArrayList<>();
  @Getter private List<Tramo> tramos = new ArrayList<>();
  @Getter @Setter private Miembro owner;

  public TrayectoCompartido(List<Miembro> miembros, List<Tramo> tramos) {
    tramos.forEach(this::validacionTrayectoCompartido);
    this.miembros = miembros;
    this.tramos = tramos;
    agregarTrayCompAmiembros(miembros);
  }

  private void validacionTrayectoCompartido(Tramo tramo) {
    if(!tramo.admiteTrayectoCompartido()) {
      throw new RuntimeException("No se puede hacer un trayecto compartido que no sea "
          + "de tipo Servico Contratado o Vehiculo Particular");
    }
  }

  private void agregarTrayCompAmiembros(List<Miembro> miembros) {
    miembros
        .stream()
        .filter(miembro -> !miembro.equals(owner)) //Por las dudas
        .forEach(miembro -> miembro.agregarTrayecto(this));
  }

  @Override
  public List<Miembro> miembros() {
    if(owner!=null && !miembros.contains(owner)) {
      // Junto al miembro que registro con los que compartio
      List<Miembro> miembrosQueMeCargaron = new ArrayList<>();
      List<Miembro> ownerList = new ArrayList<>();
      ownerList.add(owner);
      miembrosQueMeCargaron.addAll(ownerList);
      miembrosQueMeCargaron.addAll(miembros);
      return miembrosQueMeCargaron;
    } else if(owner==null) {
      throw new RuntimeException("Todavia no se registro el trayecto");
    }
    // Si por alguna razon, el que registro el trayecto esta incluido en los que compartieron,
    // devolve los que compartierion directamente
    return miembros;
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

  public double combustibleTotalUtilizado(){
    return tramos.stream().mapToDouble(Tramo::combustibleUtilizado).sum();
  }
}
