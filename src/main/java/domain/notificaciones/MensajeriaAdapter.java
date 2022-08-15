package domain.notificaciones;

public interface MensajeriaAdapter {

  /**
   * @name: enviar
   * @param suscriptor: Contacto al cual se enviará la notificación mediante todas sus suscripciones
   * @param mensaje: Mensaje a enviar
   * @return status code.
   */
  int enviar(Suscriptor suscriptor, String mensaje);

}
