package domain.trayecto;

import domain.miembros.Miembro;
import domain.trayecto.transporte.TipoDeTransporte;

import java.util.ArrayList;
import java.util.List;

public class TrayectoCompartido extends Trayecto{

  private List<Miembro> miembrosConLosQueComparto = new ArrayList<>();
  private List<Tramo> tramos = new ArrayList<>();
  private Miembro miembroQueMeCargo;

  public TrayectoCompartido(List<Miembro> miembros, List<Tramo> tramos) {
    tramos.forEach(this::validacionTrayectoCompartido);
    validacionMiembrosQueComparten(miembros);
    this.miembrosConLosQueComparto = miembros;
    this.tramos = tramos;
  }

  private void validacionMiembrosQueComparten(List<Miembro> miembros) {
    if(miembros.contains(miembroQueMeCargo)) {
      throw new RuntimeException("En los miembros que comparten el trayecto conmigo "
          + "no puedo estar yo");
    }
  }

  private void validacionTrayectoCompartido(Tramo tramo) {
    if(!(tramo.getTipo().equals(TipoDeTransporte.VEHICULO_PARTICULAR) ||
        tramo.getTipo().equals(TipoDeTransporte.SERVICIO_CONTRATADO))) {
      throw new RuntimeException("No se puede hacer un trayecto compartido que no sea "
          + "de tipo Servico Contratado o Vehiculo Particular");
    }
  }

  @Override
  public List<Miembro> getMiembrosQueMeCargaron() {
    List<Miembro> miembrosQueMeCargaron = new ArrayList<>();
    List<Miembro> miembroQueMeCargoList = new ArrayList<>();
    miembroQueMeCargoList.add(miembroQueMeCargo);
    miembrosQueMeCargaron.addAll(miembroQueMeCargoList);
    miembrosQueMeCargaron.addAll(miembrosConLosQueComparto);
    return miembrosQueMeCargaron;
  }

  @Override
  public void agregarTramo(Tramo tramo) {
    validacionTrayectoCompartido(tramo);
    tramos.add(tramo);
  }

}
