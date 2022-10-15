package domain.trayecto.transporte.nopublico;

import javax.persistence.Embeddable;

@Embeddable
public class TipoServicioContratado {
  String nombre;

  public TipoServicioContratado() {

  }

  public TipoServicioContratado(String nombre) {
    this.nombre = nombre;
  }

}
