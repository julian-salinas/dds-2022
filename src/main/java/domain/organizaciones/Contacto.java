package domain.organizaciones;

import domain.notificaciones.Notificacion;
import domain.notificaciones.TipoDeNotificacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contacto {
  private String email;
  private String whatsApp;
  private List<Notificacion> suscripciones;

  public Contacto(String email, String whatsApp) {
    this.email = email;
    this.whatsApp = whatsApp;
    this.suscripciones = new ArrayList<>();
  }

  public String getEmail() {
    return email;
  }

  public String getWhatsApp() {
    return whatsApp;
  }

  public List<Notificacion> getSuscripciones() {
    return suscripciones;
  }

  public void agregarSuscripcion(Notificacion ... suscripciones) {
    Collections.addAll(this.suscripciones, suscripciones);
  }
}
