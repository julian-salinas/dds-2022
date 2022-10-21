package domain.contrasenias;

import domain.contrasenias.validaciones.*;

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
    this.validaciones.add(new ValidacionContraseniaComun());
    this.validaciones.add(new ValidacionLongitud());
    this.validaciones.add(new ValidacionMayusculas());
    this.validaciones.add(new ValidacionMinusculas());
    this.validaciones.add(new ValidacionNumeros());
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
