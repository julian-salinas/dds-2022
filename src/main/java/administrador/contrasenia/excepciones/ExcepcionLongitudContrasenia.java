package administrador.contrasenia.excepciones;

public class ExcepcionLongitudContrasenia extends RuntimeException {
  public ExcepcionLongitudContrasenia() {
    super("Tu contraseña es muy corta, recordá que la cantidad mínima es de 8 caracteres");
  }
}