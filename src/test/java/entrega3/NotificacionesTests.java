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

public class NotificacionesTests {

  private ObserverNotificaciones observerNotificaciones;
  private Organizacion unaOrganizacion;
  Contacto unContacto, otroContacto, otroContactoMas;
  private Notificacion notificacionWhatsApp, notificacionEmail;
  private Notificador notificador;

  @BeforeEach
  void init() {
    unaOrganizacion = new Organizacion("Organizacion Fake", null, null ,null, null);

    notificacionWhatsApp = new Notificacion(new WhatsAppAdapter());
    notificacionEmail = new Notificacion(new EmailAdapter());

    unContacto = new Contacto("un.mail.muy.trucho@gmail.com", "5491152573517");
    otroContacto = new Contacto("un.mail.muy.trucho@gmail.com");
    otroContactoMas = new Contacto("5491152573517");

    unContacto.agregarSuscripciones(notificacionWhatsApp, notificacionEmail);
    otroContacto.agregarSuscripciones(notificacionEmail);
    otroContactoMas.agregarSuscripciones(notificacionEmail);

    unaOrganizacion.agregarContactos(unContacto, otroContacto, otroContactoMas);

    notificador = mock(Notificador.class);
    notificador.getInstancia().agregarOrganizacion(unaOrganizacion);
    observerNotificaciones = new ObserverNotificaciones();
  }

  @Test
  void testEnviarUnaNotificacion() {
    assertDoesNotThrow(() -> Notificador.getInstancia().notificarAUnContacto(unContacto));
  }

  @Test
  void enviarUnWhatsapp() {
    assertDoesNotThrow(() -> notificacionWhatsApp.enviar(unContacto));
  }

  @Test
  void testEnviarUnMail() {
    assertDoesNotThrow(() -> notificacionEmail.enviar(unContacto));
  }

  @Test
  void testSuscripcionesDeUnContacto() {
    assertEquals(2, unContacto.getSuscripciones().size());
    assertEquals(unContacto.getSuscripciones().get(0), notificacionWhatsApp);
  }

  @Test
  void testFuncionamientoDelNotificador() {
    Notificador.getInstancia().notificarATodos();
  }

  @Test
  void testFuncionamientoDelPlanificador() {
    String[] args = new String[] {"123"};
    assertDoesNotThrow(() -> Planificador.main(args));
  }
}
