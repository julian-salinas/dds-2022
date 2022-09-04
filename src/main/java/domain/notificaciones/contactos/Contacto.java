package domain.notificaciones.contactos;

import domain.notificaciones.Suscriptor;

public class Contacto implements Suscriptor {
  private String email = null;
  private String whatsApp = null;

  public Contacto(String email, String whatsApp) {
    ValidadorDatoDeContacto validadorDatoDeContacto = new ValidadorDatoDeContacto();
    validadorDatoDeContacto.validarEmail(email);
    validadorDatoDeContacto.validarWhatsapp(whatsApp);
    this.email = email;
    this.whatsApp = whatsApp;
  }

  public Contacto(String datoDeContacto) {
    ValidadorDatoDeContacto validador = new ValidadorDatoDeContacto();

    if (validador.emailEsValido(datoDeContacto)) {
      this.email = datoDeContacto;
    }
    else if (validador.whatsAppEsValido(datoDeContacto)) {
      this.whatsApp = datoDeContacto;
    }
    else {
      throw new RuntimeException("Que me pasaste amigo?");
    }
  }

  public String getEmail() {
    return email;
  }

  public String getWhatsApp() {
    return whatsApp;
  }

}
