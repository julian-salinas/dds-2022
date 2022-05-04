package administrador.contrasenia;

import administrador.contrasenia.excepciones.ExcepcionContraseniaNoContieneMinusculas;
import org.omg.SendingContext.RunTime;

public class ValidacionMinusculas extends Validacion {

  @Override
  public boolean condicion(String password) {
    // devuelve true si una contraseña no contiene minúsculas
    return password.matches(".*[a-z].*");
  }

  @Override
  protected RuntimeException error() {
    return new ExcepcionContraseniaNoContieneMinusculas();
  }
}
