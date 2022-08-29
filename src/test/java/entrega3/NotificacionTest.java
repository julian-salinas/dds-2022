package entrega3;

import domain.notificaciones.MensajeriaAdapter;
import domain.notificaciones.Notificacion;
import domain.notificaciones.Planificador;
import domain.notificaciones.adapters.EmailAdapter;
import domain.notificaciones.adapters.WhatsAppAdapter;
import domain.organizaciones.*;
import domain.repositorios.RepoNotificaciones;
import domain.repositorios.RepoOrganizaciones;
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

public class NotificacionTest {
  private Organizacion unaOrganizacion;
  private Contacto unContacto, otroContacto, otroContactoMas;
  private Notificacion notificacionWhatsApp, notificacionEmail;

  private MensajeriaAdapter whatsAppAdapter, emailAdapter;

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

    whatsAppAdapter = mock(WhatsAppAdapter.class);
    emailAdapter = mock(EmailAdapter.class);

    notificacionWhatsApp = new Notificacion(whatsAppAdapter);
    notificacionEmail = new Notificacion(emailAdapter);

    RepoNotificaciones.getInstancia().agregar(notificacionEmail, notificacionWhatsApp);

    unContacto = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");
    otroContacto = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");
    otroContactoMas = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");

    unaOrganizacion.agregarContactos(unContacto, otroContacto, otroContactoMas);
    RepoOrganizaciones.instance().agregarOrganizaciones(unaOrganizacion);
  }

  @Disabled("Este test no utiliza mocks")
  @Test
  void testEnviarUnWhatsappPosta() {
    Notificacion unaNotificacionWhatsApp = new Notificacion(new WhatsAppAdapter(ACCOUNT_SID, AUTH_TOKEN, TWILIO_PHONE_NUMBER));
    assertDoesNotThrow(() -> unaNotificacionWhatsApp.notificar(unContacto));
  }

  @Disabled("Este test no utiliza mocks")
  @Test
  void testEnviarUnMailPosta() {
    Notificacion unaNotificacionEmail = new Notificacion(new EmailAdapter(SENDGRID_API_KEY, EMAIL));
    assertDoesNotThrow(() -> unaNotificacionEmail.notificar(unContacto));
  }

  @Test
  void enviarUnWhatsapp() {
    when(whatsAppAdapter.enviar(unContacto, "<<<guia de recomendaciones>>>")).thenReturn(202);

    int status =  notificacionWhatsApp.notificar(unContacto);
    assertEquals(202, status);

    RepoOrganizaciones.instance().limpiar();
  }

  @Test
  void testEnviarUnMail() {
    when(emailAdapter.enviar(unContacto, "<<<guia de recomendaciones>>>")).thenReturn(202);

    int status = notificacionEmail.notificar(unContacto);
    assertEquals(202, status);

    RepoOrganizaciones.instance().limpiar();
  }

  @Test
  void testFuncionamientoDelPlanificador() {
    when(emailAdapter.enviar(unContacto, "<<<guia de recomendaciones>>>")).thenReturn(202);
    when(whatsAppAdapter.enviar(unContacto, "<<<guia de recomendaciones>>>")).thenReturn(202);

    String[] args = new String[] {"123"};
    assertDoesNotThrow(() -> Planificador.main(args));

    RepoOrganizaciones.instance().limpiar();
  }
}
