package domain.passwords;

import domain.passwords.validaciones.Validacion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contrasenia {
  private List<Validacion> validaciones;

  public Contrasenia(Validacion... validaciones) {
    this.validaciones = new ArrayList<>();
    Collections.addAll(this.validaciones, validaciones);
  }

  public Contrasenia() {
    this.validaciones = new ArrayList<>();
  }

  public void setValidaciones(Validacion... validaciones) {
    Collections.addAll(this.validaciones, validaciones);
  }

  public void validarContrasenia(String unaContrasenia) {
    // Realizar la validacion con cada una de las clases de validación
    this.validaciones.stream().forEach(validacion -> this.validarUnAspecto(unaContrasenia, validacion));
  }

  public void validarUnAspecto(String contrasenia, Validacion validacion) {
    if (!validacion.condicion(contrasenia)) {
      throw validacion.error();
    }
  }
}
