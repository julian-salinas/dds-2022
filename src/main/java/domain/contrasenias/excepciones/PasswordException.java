package domain.contrasenias.excepciones;

public class PasswordException extends RuntimeException {
  public PasswordException(String message) {
    super(message);
  }
}
