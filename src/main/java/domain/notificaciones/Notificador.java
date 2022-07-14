package domain.notificaciones;

import domain.organizaciones.Contacto;
import domain.organizaciones.Organizacion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Notificador {
  private List<Organizacion> organizaciones = new ArrayList<>();
  private static Notificador instancia = null;

  public static Notificador getInstancia() {
    if (instancia == null) {
      instancia = new Notificador();
    }

    return instancia;
  }

  public void agregarOrganizacion(Organizacion ... nuevasOrganizaciones) {
    Collections.addAll(organizaciones, nuevasOrganizaciones);
  }

  public void notificarAUnContacto(Contacto contacto) {
    contacto.getSuscripciones().forEach(notificacion -> notificacion.enviar(contacto));
  }

  public void notificarContactosDeOrganizacion(Organizacion organizacion) {
    organizacion.getContactos().stream().forEach(contacto -> notificarAUnContacto(contacto));
  }

  public void notificarATodos() {
    this.organizaciones.stream().forEach(organizacion -> notificarContactosDeOrganizacion(organizacion));
  }

}
