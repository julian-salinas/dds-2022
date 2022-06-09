package domain.passwords.validaciones;

import domain.passwords.exceptions.PasswordException;

public class ValidacionMayusculas implements Validacion {

  @Override
  public boolean condicion(String password) {
    // devuelve true si una contraseña contiene al menos una mayuscula
    return password.matches(".*[A-Z].*");
  }

  @Override
  public PasswordException error() {
    return new PasswordException("Tu contraseña debe contener mayusculas");
  }
}
