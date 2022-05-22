package passwords.validaciones;

import passwords.exceptions.NumerosException;

public class ValidacionNumeros extends Validacion {
  @Override
  public boolean condicion(String password) {
    // devuelve true si una contraseña contiene al menos un numero
    return password.chars().anyMatch(Character::isDigit);
  }

  @Override
  protected RuntimeException error() {
    return new NumerosException();
  }
}
