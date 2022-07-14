package domain.notificaciones;

import domain.organizaciones.Contacto;

public class Notificacion {

  private NotificacionAdapter notificacionAdapter;

  public Notificacion(NotificacionAdapter notificacionAdapter) {
    this.notificacionAdapter = notificacionAdapter;
  }

  private String getMensajeNotificacion() {
    return "Recordá chequear nuestra guía de recomendaciones cada tanto! <link>";
  }

  public int enviar(Contacto contacto) {
    return this.notificacionAdapter.enviar(contacto, this.getMensajeNotificacion());
  }
}
