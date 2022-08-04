package entrega3;

import domain.notificaciones.Notificacion;
import domain.notificaciones.Notificador;
import domain.notificaciones.ObserverNotificaciones;
import domain.notificaciones.Planificador;
import domain.notificaciones.adapters.EmailAdapter;
import domain.notificaciones.adapters.WhatsAppAdapter;
import domain.organizaciones.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotificacionesMockeadasTests {

  private ObserverNotificaciones observerNotificaciones;
  private Organizacion unaOrganizacion;
  Contacto unContacto, otroContacto, otroContactoMas;
  private Notificacion notificacionWhatsApp, notificacionEmail;
  WhatsAppAdapter whatsAppAdapter;
  EmailAdapter emailAdapter;
  Notificacion notificacion;

  @BeforeEach
  void init() {
    unaOrganizacion = new Organizacion("Organizacion Fake", null, null ,null, null);

    whatsAppAdapter = mock(WhatsAppAdapter.class);
    emailAdapter = mock(EmailAdapter.class);

    notificacionWhatsApp = new Notificacion(whatsAppAdapter);
    notificacionEmail = new Notificacion(emailAdapter);

    unContacto = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");
    otroContacto = new Contacto("un.mail.muy.trucho@gmail.com");
    otroContactoMas = new Contacto("5491152573517");

    unContacto.agregarSuscripciones(notificacionWhatsApp, notificacionEmail);
    otroContacto.agregarSuscripciones(notificacionEmail);
    otroContactoMas.agregarSuscripciones(notificacionEmail);

    unaOrganizacion.agregarContactos(unContacto, otroContacto, otroContactoMas);

    Notificador.getInstancia().agregarOrganizacion(unaOrganizacion);
    observerNotificaciones = new ObserverNotificaciones();
  }

  @Test
  void enviarUnWhatsapp() {
    when(whatsAppAdapter.enviar(unContacto, "Recordá chequear nuestra guía de recomendaciones cada tanto! <link>"))
        .thenReturn(202);

    int status =  notificacionWhatsApp.enviar(unContacto);
    assertEquals(202, status);
  }

  @Test
  void testEnviarUnMail() {
    when(emailAdapter.enviar(unContacto, "Recordá chequear nuestra guía de recomendaciones cada tanto! <link>"))
        .thenReturn(202);

    int status = notificacionEmail.enviar(unContacto);
    assertEquals(202, status);
  }

  @Test
  void testFuncionamientoDelNotificador() {
    when(whatsAppAdapter.enviar(unContacto, "Recordá chequear nuestra guía de recomendaciones cada tanto! <link>"))
        .thenReturn(202);

    when(whatsAppAdapter.enviar(unContacto, "Recordá chequear nuestra guía de recomendaciones cada tanto! <link>"))
        .thenReturn(202);

    assertDoesNotThrow(() -> Notificador.getInstancia().notificarATodos());
  }

  @Test
  void testFuncionamientoDelPlanificador() {
    String[] args = new String[] {"123"};
    assertDoesNotThrow(() -> Planificador.main(args));
  }
}
