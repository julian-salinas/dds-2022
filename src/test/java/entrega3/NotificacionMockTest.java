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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotificacionMockTest {
  private Organizacion unaOrganizacion;
  Contacto unContacto, otroContacto, otroContactoMas;
  private Notificacion notificacionWhatsApp, notificacionEmail;
  private MensajeriaAdapter whatsAppAdapter, emailAdapter;

  @BeforeEach
  void init() {

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

  @Test
  void enviarUnWhatsapp() {
    when(whatsAppAdapter.enviar(unContacto, "<<<guia de recomendaciones>>>")).thenReturn(202);

    int status =  notificacionWhatsApp.notificar(unContacto);
    assertEquals(202, status);
  }

  @Test
  void testEnviarUnMail() {
    when(emailAdapter.enviar(unContacto, "<<<guia de recomendaciones>>>")).thenReturn(202);

    int status = notificacionEmail.notificar(unContacto);
    assertEquals(202, status);
  }

  @Test
  void testFuncionamientoDelPlanificador() {
    when(emailAdapter.enviar(unContacto, "<<<guia de recomendaciones>>>")).thenReturn(202);
    when(whatsAppAdapter.enviar(unContacto, "<<<guia de recomendaciones>>>")).thenReturn(202);

    String[] args = new String[] {"123"};
    assertDoesNotThrow(() -> Planificador.main(args));
  }
}