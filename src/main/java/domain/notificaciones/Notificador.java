package domain.notificaciones;

import domain.organizaciones.Contacto;
import domain.organizaciones.Organizacion;

public class Notificador {
  private static Notificador notificador = null;

  public static Notificador getInstance() {
    if (notificador == null) {
      notificador = new Notificador();
    }
    return notificador;
  }

  public void notificarAUnContacto(Contacto contacto) {
    contacto.getSuscripciones().forEach(notificacion -> notificacion.enviar(contacto));
  }

  public void notificarContactosDeOrganizacion(Organizacion organizacion) {
    organizacion.getContactos().forEach(contacto -> notificarAUnContacto(contacto));
  }
}
