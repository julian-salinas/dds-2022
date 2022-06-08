package domain.passwords.validaciones;

import domain.passwords.exceptions.PasswordException;

public class ValidacionLongitud implements Validacion {

  @Override
  public boolean condicion(String password) {
    /*
    Devuelve true si la contraseña tiene longitud mayor a 8
     */
    return password.length() >= 8;
  }

  @Override
  public PasswordException error() {
    return new PasswordException("Tu contraseña es muy corta, recordá que la cantidad mínima es de 8 caracteres");
  }
}
