package administrador.contrasenia.excepciones;

public class ExcepcionContraseniaEsNumerica extends RuntimeException {
  public ExcepcionContraseniaEsNumerica() {
    super("Tu contraseña debe tener numeros");
  }
}
