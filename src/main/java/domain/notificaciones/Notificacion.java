package domain.notificaciones;

import domain.organizaciones.Contacto;

public class Notificacion {

  private NotificacionAdapter notificacionAdapter;

  public Notificacion(NotificacionAdapter notificacionAdapter) {
    this.notificacionAdapter = notificacionAdapter;
  }

  public String getMensajeNotificacion() {
    return "Recordá chequear nuestra guía de recomendaciones cada tanto! <link>";
  }

  public void enviar(Contacto contacto) {
    this.notificacionAdapter.enviar(contacto, this.getMensajeNotificacion());
  }
}
