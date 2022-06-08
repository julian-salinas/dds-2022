package domain.passwords.validaciones;

import domain.passwords.exceptions.PasswordException;

public class ValidacionNumeros implements Validacion {
  @Override
  public boolean condicion(String password) {
    // devuelve true si una contraseña contiene al menos un numero
    return password.chars().anyMatch(Character::isDigit);
  }

  @Override
  public PasswordException error() {
    return new PasswordException("Tu contraseña debe tener numeros");
  }
}
