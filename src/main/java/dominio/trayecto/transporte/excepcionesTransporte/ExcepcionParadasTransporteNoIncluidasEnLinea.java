package dominio.trayecto.transporte.excepcionesTransporte;

public class ExcepcionParadasTransporteNoIncluidasEnLinea extends RuntimeException{
  public ExcepcionParadasTransporteNoIncluidasEnLinea() {
    super("Las paradas no forman parte de la linea");
  }
}
