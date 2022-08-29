package domain.notificaciones;

import domain.repositorios.RepoOrganizaciones;

public class Notificacion {

  private MensajeriaAdapter mensajeriaAdapter;

  public Notificacion(MensajeriaAdapter mensajeriaAdapter) {
    this.mensajeriaAdapter = mensajeriaAdapter;
  }

  private String getMensajeNotificacion() {
    return "<<<guia de recomendaciones>>>";
  }

  public int notificar(Suscriptor suscriptor) {
    int status_code = this.mensajeriaAdapter.enviar(suscriptor, this.getMensajeNotificacion());
    return status_code;
  }

  public void notificarOrganizaciones() {
    RepoOrganizaciones.instance().getContactos().stream().forEach(contacto -> this.notificar(contacto));
  }
}