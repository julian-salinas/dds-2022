package presentacion.errores;

import lombok.Getter;
import lombok.Setter;

public class InError {
  @Getter @Setter String descripcion;
  @Setter
  boolean error = false;

  public InError(String descripcion) {
    this.descripcion = descripcion;
  }

  public InError() {

  }


  public boolean getError() {
    return error;
  }

}
