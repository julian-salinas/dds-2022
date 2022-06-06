package domain.passwords.exceptions;

public class MayusculasException extends RuntimeException {
  public MayusculasException() {
    super("Tu contraseña debe contener mayusculas");
  }
}

