package administrador.contrasenia.excepciones;

public class ExcepcionContraseniaNoContieneMinusculas extends RuntimeException {
  public ExcepcionContraseniaNoContieneMinusculas() {
    super("Tu contraseña debe contener minusculas");
  }
}
