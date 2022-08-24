package domain.contrasenias.validaciones;

import domain.contrasenias.excepciones.PasswordException;

public class ValidacionMinusculas implements Validacion {

  @Override
  public boolean condicion(String password) {
    // devuelve true si una contraseña contiene al menos una minúscula
    return password.matches(".*[a-z].*");
  }

  @Override
  public PasswordException error() {
    return new PasswordException("Tu contraseña debe contener minusculas");
  }
}
