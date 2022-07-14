package domain.notificaciones;

import domain.organizaciones.Contacto;

public interface NotificacionAdapter {

  /**
   * @name: enviar
   * @param contacto: Contacto al cual se enviará la notificación mediante todas sus suscripciones
   * @param mensaje: Mensaje a enviar
   * @return status code. -1 indica error de ejecución
   */
  int enviar(Contacto contacto, String mensaje);

}
