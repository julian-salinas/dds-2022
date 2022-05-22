package passwords.exceptions;

public class ContraseniaComunException extends RuntimeException {
  public ContraseniaComunException() {
    super("Tu contraseña se encuentra en el top 1000 peores contraseñas");
  }
}
