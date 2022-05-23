package passwords.exceptions;

public class NumerosException extends RuntimeException {
  public NumerosException() {
    super("Tu contraseña debe tener numeros");
  }
}
