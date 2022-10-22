package domain.contrasenias.excepciones;

import lombok.Getter;

public class PasswordException extends RuntimeException {
  @Getter String message;
  public PasswordException(String message) {
    super(message);
    this.message = message;
  }
}
