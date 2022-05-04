package administrador.contrasenia;

import administrador.contrasenia.excepciones.ExcepcionLongitudContrasenia;

public class ValidacionLongitud extends Validacion {

  @Override
  public boolean condicion(String password) {
    return password.length() >= 8;
  }

  @Override
  public RuntimeException error() {
    return new ExcepcionLongitudContrasenia();
  }
}
