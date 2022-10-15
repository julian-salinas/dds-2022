package domain.trayecto.transporte;

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
