package dominio.trayecto.transporte.excepciones;

public class ExcepcionParadasTransporteNoIncluidasEnLinea extends RuntimeException {
  public ExcepcionParadasTransporteNoIncluidasEnLinea() {
    super("Las paradas no forman parte de la linea");
  }
}
