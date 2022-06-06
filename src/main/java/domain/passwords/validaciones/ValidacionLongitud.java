package domain.passwords.validaciones;

import domain.passwords.exceptions.LongitudException;

public class ValidacionLongitud extends Validacion {

  @Override
  public boolean condicion(String password) {
    /*
    Devuelve true si la contraseña tiene longitud mayor a 8
     */
    return password.length() >= 8;
  }

  @Override
  public RuntimeException error() {
    return new LongitudException();
  }
}
