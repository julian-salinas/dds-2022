package domain.trayecto.transporte;
import domain.servicios.geodds.ServicioGeoDds;
import domain.servicios.geodds.entidades.Distancia;
import domain.ubicaciones.Ubicacion;

public class Pie implements MedioDeTransporte {
  private Ubicacion direccionInicio;
  private Ubicacion direccionFin;

  public Pie(Ubicacion direccionInicio, Ubicacion direccionFin) {
    this.direccionInicio = direccionInicio;
    this.direccionFin = direccionFin;
  }

  public int getDistancia() {
    // TODO
    ServicioGeoDds api = ServicioGeoDds.getInstancia();
    Distancia distancia;
    // distanciaEntreUbicaciones tira IOException dice, y me quiere forzar a q getDistancia() haga lo mismo
    // y no me pinta
    distancia = api.distanciaEntreUbicaciones(direccionInicio.getLocalidad(), direccionInicio.getCalle(),
        direccionInicio.getAltura(), direccionFin.getLocalidad(), direccionInicio.getCalle(), direccionFin.getAltura());
    // Podemos cambiar a que devuelva Double, o incluso Distancia
    return (int) distancia.valor;
  }

}
