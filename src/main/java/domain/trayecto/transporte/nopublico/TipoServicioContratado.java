package domain.trayecto.transporte.nopublico;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class TipoServicioContratado {
  private String nombre;

  public TipoServicioContratado() {

  }

  public TipoServicioContratado(String nombre) {
    this.nombre = nombre;
  }

}
