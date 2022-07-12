package domain.trayecto;

import domain.miembros.Miembro;
import domain.trayecto.transporte.TipoDeTransporte;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TrayectoCompartido extends Trayecto{

  private List<Miembro> miembros = new ArrayList<>();
  private List<Tramo> tramos = new ArrayList<>();
  @Getter private Miembro owner;

  public TrayectoCompartido(List<Miembro> miembros, List<Tramo> tramos) {
    tramos.forEach(this::validacionTrayectoCompartido);
    this.miembros = miembros;
    this.tramos = tramos;
  }

  private void validacionTrayectoCompartido(Tramo tramo) {
    if(!(tramo.getTipo().equals(TipoDeTransporte.VEHICULO_PARTICULAR) ||
        tramo.getTipo().equals(TipoDeTransporte.SERVICIO_CONTRATADO))) {
      throw new RuntimeException("No se puede hacer un trayecto compartido que no sea "
          + "de tipo Servico Contratado o Vehiculo Particular");
    }
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
      throw new RuntimeException("Todavia no se registro trayecto");
    }
    // Si por alguna razon, el que registro el trayecto esta incluido en los que compartieron,
    // devolve los que compartierion directamente
    return miembros;
  }

  @Override
  public void agregarTramo(Tramo tramo) {
    validacionTrayectoCompartido(tramo);
    tramos.add(tramo);
  }

}
