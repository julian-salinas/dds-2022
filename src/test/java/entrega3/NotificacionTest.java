package entrega3;

import domain.notificaciones.NotificacionEmail;
import domain.notificaciones.NotificacionWhatsapp;
import domain.notificaciones.contactos.Contacto;
import domain.notificaciones.Notificacion;
import domain.notificaciones.Planificador;
import domain.organizaciones.*;
import repositorios.RepositorioNotificaciones;
import repositorios.RepositorioOrganizaciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// TODO: fixear tests de notificaciones (debe ser por los Repos) y agregar mejores tests.

public class NotificacionTest {
  private Organizacion unaOrganizacion;
  private Contacto unContacto, otroContacto, otroContactoMas;
  private Notificacion notificacionWhatsApp, notificacionEmail, mockNotificacionEmail, mockNotificacionWhatsApp;

  private String SENDGRID_API_KEY, EMAIL, ACCOUNT_SID, AUTH_TOKEN, TWILIO_PHONE_NUMBER;

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

    // whatsAppAdapter = mock(WhatsAppAdapter.class);
    // emailAdapter = mock(EmailAdapter.class);

    notificacionWhatsApp = new NotificacionWhatsapp(ACCOUNT_SID, AUTH_TOKEN, TWILIO_PHONE_NUMBER);
    notificacionEmail = new NotificacionEmail(SENDGRID_API_KEY, EMAIL);

    mockNotificacionWhatsApp = mock(NotificacionWhatsapp.class);
    mockNotificacionEmail = mock(NotificacionEmail.class);

    RepositorioNotificaciones.getInstance().add(notificacionEmail);
    RepositorioNotificaciones.getInstance().add(notificacionWhatsApp);

    unContacto = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");
    otroContacto = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");
    otroContactoMas = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");

    unaOrganizacion.agregarContactos(unContacto, otroContacto, otroContactoMas);
    RepositorioOrganizaciones.getInstance().add(unaOrganizacion);
  }

  @Disabled("Este test no utiliza mocks")
  @Test
  void testEnviarUnWhatsappPosta() {
    Notificacion unaNotificacionWhatsApp = new NotificacionWhatsapp(ACCOUNT_SID, AUTH_TOKEN, TWILIO_PHONE_NUMBER);
    assertDoesNotThrow(() -> unaNotificacionWhatsApp.notificar(unContacto));
  }

  @Disabled("Este test no utiliza mocks")
  @Test
  void testEnviarUnMailPosta() {
    Notificacion unaNotificacionEmail = new NotificacionEmail(SENDGRID_API_KEY, EMAIL);
    assertDoesNotThrow(() -> unaNotificacionEmail.notificar(unContacto));
  }

  @Test
  void enviarUnWhatsapp() {
    when(mockNotificacionWhatsApp.notificar(unContacto)).thenReturn(202);

    int status =  notificacionWhatsApp.notificar(unContacto);
    assertEquals(202, status);

    RepositorioOrganizaciones.getInstance().clean();
  }

  @Test
  void testEnviarUnMail() {
    when(mockNotificacionEmail.notificar(unContacto)).thenReturn(202);

    int status = notificacionEmail.notificar(unContacto);
    assertEquals(202, status);

    RepositorioOrganizaciones.getInstance().clean();
  }

  @Test
  void testFuncionamientoDelPlanificador() {
    when(mockNotificacionEmail.notificar(unContacto)).thenReturn(202);
    when(mockNotificacionWhatsApp.notificar(unContacto)).thenReturn(202);

    String[] args = new String[] {"123"};
    assertDoesNotThrow(() -> Planificador.main(args));

    RepositorioOrganizaciones.getInstance().clean();
  }
}
