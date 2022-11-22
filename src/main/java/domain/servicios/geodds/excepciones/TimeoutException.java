package domain.servicios.geodds.excepciones;

public class TimeoutException extends RuntimeException {
  public TimeoutException(String msg) {
    super(msg);
  }
}
