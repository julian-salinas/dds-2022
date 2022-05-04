package administrador.contrasenia;

import administrador.contrasenia.excepciones.ExcepcionContraseniaNoContieneMayusculas;
import administrador.contrasenia.excepciones.ExcepcionContraseniaNoContieneMinusculas;

public class ValidacionMayusculas extends Validacion{

  @Override
  public boolean condicion(String password) {
    // devuelve true si una contraseña no contiene mayusculas
    return password.matches(".*[A-Z].*");
  }

  @Override
  protected RuntimeException error() {
    return new ExcepcionContraseniaNoContieneMayusculas();
  }
}
