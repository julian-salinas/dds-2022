package domain.passwords.validaciones;

import domain.passwords.exceptions.PasswordException;

public interface Validacion {

  boolean condicion(String contrasenia);

  PasswordException error();
}
