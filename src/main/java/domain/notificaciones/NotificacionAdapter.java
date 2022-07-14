package domain.notificaciones;

import domain.organizaciones.Contacto;

public interface NotificacionAdapter {

  void enviar(Contacto contacto, String mensaje);

}
