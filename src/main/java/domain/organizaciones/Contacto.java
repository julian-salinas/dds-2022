package domain.organizaciones;

import domain.notificaciones.Notificacion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contacto {
  private String email;
  private String whatsApp;
  private List<Notificacion> suscripciones;

  public Contacto(String email, String whatsApp) {
    ValidadorDatoDeContacto validadorDatoDeContacto = ValidadorDatoDeContacto.getInstancia();
    validadorDatoDeContacto.validarEmail(email);
    validadorDatoDeContacto.validarWhatsapp(whatsApp);
    this.email = email;
    this.whatsApp = whatsApp;
    this.suscripciones = new ArrayList<>();
  }

  public Contacto(String datoDeContacto) {
    ValidadorDatoDeContacto validador = ValidadorDatoDeContacto.getInstancia();

    if (validador.emailEsValido(datoDeContacto)) {
      this.email = datoDeContacto;
    }
    else if (validador.whatsAppEsValido(datoDeContacto)) {
      this.whatsApp = datoDeContacto;
    }
    else {
      throw new RuntimeException("Que me pasaste amigo?");
    }

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

  public void agregarSuscripciones(Notificacion ... suscripciones) {
    Collections.addAll(this.suscripciones, suscripciones);
  }

}
