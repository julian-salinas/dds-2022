package passwords.exceptions;

public class MinusculasException extends RuntimeException {
  public MinusculasException() {
    super("Tu contraseña debe contener minusculas");
  }
}
