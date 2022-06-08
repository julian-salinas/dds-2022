package domain.trayecto;

import domain.miembros.Miembro;
import domain.trayecto.transporte.TipoDeTransporte;

import java.util.ArrayList;
import java.util.List;

public class TrayectoCompartido extends Trayecto{

  List<Miembro> miembrosQueCompartieron = new ArrayList<>();
  private List<Tramo> tramos = new ArrayList<>();

  public TrayectoCompartido(List<Miembro> miembros, List<Tramo> tramos) {
    tramos.forEach(this::validacionTrayectoCompartido);
    this.miembrosQueCompartieron = miembros;
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
  public void agregarTramo(Tramo tramo) {
    validacionTrayectoCompartido(tramo);
    tramos.add(tramo);
  }

}
