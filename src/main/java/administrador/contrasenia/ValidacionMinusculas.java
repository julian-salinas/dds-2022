package administrador.contrasenia;

import administrador.contrasenia.excepciones.ExcepcionContraseniaNoContieneMinusculas;

public class ValidacionMinusculas extends Validacion {

  @Override
  public boolean condicion(String password) {
    // devuelve true si una contraseña contiene al menos una minúscula
    return password.matches(".*[a-z].*");
  }

  @Override
  protected RuntimeException error() {
    return new ExcepcionContraseniaNoContieneMinusculas();
  }
}
