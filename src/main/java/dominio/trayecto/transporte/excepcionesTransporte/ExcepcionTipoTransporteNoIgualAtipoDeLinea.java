package dominio.trayecto.transporte.excepcionesTransporte;

public class ExcepcionTipoTransporteNoIgualAtipoDeLinea extends RuntimeException{
  public ExcepcionTipoTransporteNoIgualAtipoDeLinea() {
    super("El tipo no es el mismo tipo de la linea");
  }
}
