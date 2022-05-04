package administrador.contrasenia;

import administrador.contrasenia.excepciones.ExcepcionLongitudContrasenia;

public class ValidacionLongitud extends Validacion {

  @Override
  public boolean condicion(String password) {
    /*
    Devuelve true si la contraseña tiene longitud mayor a 8
     */
    return password.length() >= 8;
  }

  @Override
  public RuntimeException error() {
    return new ExcepcionLongitudContrasenia();
  }
}
