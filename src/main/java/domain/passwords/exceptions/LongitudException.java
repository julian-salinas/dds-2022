package domain.passwords.exceptions;

public class LongitudException extends RuntimeException {
  public LongitudException() {
    super("Tu contraseña es muy corta, recordá que la cantidad mínima es de 8 caracteres");
  }
}