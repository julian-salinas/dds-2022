package domain.passwords.validaciones;

public abstract class Validacion {

  public void validarContrasenia(String contrasenia) {
    if (!condicion(contrasenia)) {
      throw error();
    }
  }

  protected abstract boolean condicion(String contrasenia);

  protected abstract RuntimeException error();
}
