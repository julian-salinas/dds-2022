package domain.contrasenias.validaciones;

import domain.contrasenias.excepciones.PasswordException;

public interface Validacion {

  boolean condicion(String contrasenia);

  PasswordException error();
}
