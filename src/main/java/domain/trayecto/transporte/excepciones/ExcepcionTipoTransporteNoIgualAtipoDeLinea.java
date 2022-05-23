package domain.trayecto.transporte.excepciones;

public class ExcepcionTipoTransporteNoIgualAtipoDeLinea extends RuntimeException {
  public ExcepcionTipoTransporteNoIgualAtipoDeLinea() {
    super("El tipo no es el mismo tipo de la linea");
  }
}
