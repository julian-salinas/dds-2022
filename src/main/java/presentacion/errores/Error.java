package presentacion.errores;

import lombok.Getter;
import lombok.Setter;

public class Error {
  @Getter @Setter String descripcion;
  @Setter
  boolean error = false;

  public Error(String descripcion) {
    this.descripcion = descripcion;
  }

  public Error() {

  }


  public boolean getError() {
    return error;
  }

}
