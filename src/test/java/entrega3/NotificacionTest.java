package entrega3;

import domain.notificaciones.Notificacion;
import domain.notificaciones.Planificador;
import domain.notificaciones.adapters.EmailAdapter;
import domain.notificaciones.adapters.WhatsAppAdapter;
import domain.organizaciones.*;
import domain.repositorios.RepoNotificaciones;
import domain.repositorios.RepoOrganizaciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;

public class NotificacionTest {
  private Organizacion unaOrganizacion;
  Contacto unContacto, otroContacto, otroContactoMas;
  private Notificacion notificacionWhatsApp, notificacionEmail;

  String SENDGRID_API_KEY, EMAIL, ACCOUNT_SID, AUTH_TOKEN, TWILIO_PHONE_NUMBER;

  @BeforeEach
  void init() {

    // Leer archivo properties
    try {
      InputStream input = new FileInputStream("src/main/java/domain/local.properties");
      Properties properties = new Properties();
      properties.load(input);

      SENDGRID_API_KEY = properties.getProperty("SENDGRID_API_KEY");;
      EMAIL = properties.getProperty("OWN_EMAIL");
      ACCOUNT_SID = properties.getProperty("TWILIO_ACCOUNT_SID");;
      AUTH_TOKEN = properties.getProperty("TWILIO_AUTH_TOKEN");
      TWILIO_PHONE_NUMBER = properties.getProperty("TWILIO_PHONE_NUMBER");
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    unaOrganizacion = new Organizacion("Organizacion Fake", null, null ,null, null);

    notificacionWhatsApp = new Notificacion(new WhatsAppAdapter(ACCOUNT_SID, AUTH_TOKEN, TWILIO_PHONE_NUMBER));
    notificacionEmail = new Notificacion(new EmailAdapter(SENDGRID_API_KEY, EMAIL));
    RepoNotificaciones.getInstancia().agregar(notificacionEmail, notificacionWhatsApp);

    unContacto = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");
    otroContacto = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");
    otroContactoMas = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");
    unaOrganizacion.agregarContactos(unContacto, otroContacto, otroContactoMas);
    RepoOrganizaciones.instance().agregarOrganizaciones(unaOrganizacion);
  }

  @Test
  void testEnviarUnWhatsapp() {
    assertDoesNotThrow(() -> notificacionWhatsApp.notificar(unContacto));
  }

  @Test
  void testEnviarUnMail() {
    assertDoesNotThrow(() -> notificacionEmail.notificar(unContacto));
  }

  @Test
  void testFuncionamientoDelPlanificador() {
    String[] args = new String[] {"123"};
    assertDoesNotThrow(() -> Planificador.main(args));
  }
}
