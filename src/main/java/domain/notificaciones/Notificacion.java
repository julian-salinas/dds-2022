package domain.notificaciones;

import domain.PersistenceEntity;
import domain.database.EntidadPersistente;
import domain.notificaciones.adapters.MensajeriaAdapter;
import domain.repositorios.RepositorioContactos;

import javax.persistence.*;

@Entity
@Table(name = "notificaciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "servicio")
public abstract class Notificacion extends PersistenceEntity {

  public Notificacion(){}

  @Transient
  private MensajeriaAdapter mensajeriaAdapter;

  @Transient
  private TipoDeNotificacion tipoDeNotificacion;

  public Notificacion(MensajeriaAdapter mensajeriaAdapter, TipoDeNotificacion tipoDeNotificacion) {
    this.mensajeriaAdapter = mensajeriaAdapter;
    this.tipoDeNotificacion = tipoDeNotificacion;
  }

  private String getMensajeNotificacion() {
    return "<<<guia de recomendaciones>>>";
  }

  public TipoDeNotificacion getTipoDeNotificacion() {
    return tipoDeNotificacion;
  }

  public int notificar(Suscriptor suscriptor) {
    int status_code = this.mensajeriaAdapter.enviar(suscriptor, this.getMensajeNotificacion());
    return status_code;
  }

}