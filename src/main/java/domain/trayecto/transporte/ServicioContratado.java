package domain.trayecto.transporte;

public class ServicioContratado implements MedioDeTransporte {

  private Direccion direccionInicio;
  private Direccion direccionFin;
  private final TipoServicioContratado tipo;

  public ServicioContratado(TipoServicioContratado tipo,
                            Direccion direccionInicio, Direccion direccionFin) {
    this.tipo = tipo;
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

}
