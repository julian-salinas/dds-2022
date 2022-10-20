package domain.trayecto.transporte.excepciones;

public class ExcepcionMultiplesParadasConMismaUbicacion extends RuntimeException {
  public ExcepcionMultiplesParadasConMismaUbicacion() {
    super("Hay mas de una parada con la misma ubicacion");
  }
}
