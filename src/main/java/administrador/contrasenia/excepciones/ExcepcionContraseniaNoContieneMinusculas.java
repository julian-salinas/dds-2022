package administrador.contrasenia.excepciones;

public class ExcepcionContraseniaNoContieneMinusculas extends RuntimeException {
  public ExcepcionContraseniaNoContieneMinusculas() {
    super("Tu contraseña sólo contiene mayúsculas");
  }
}
