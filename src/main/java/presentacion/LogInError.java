package presentacion;

import lombok.Getter;
import lombok.Setter;

public class LogInError {
  @Getter String descripcion;
  @Setter
  boolean error = false;

  public LogInError(String descripcion) {
    this.descripcion = descripcion;
  }

  public boolean getError() {
    return error;
  }

}
