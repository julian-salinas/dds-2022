package domain.notificaciones.contactos;

import domain.PersistenceEntity;
import domain.database.EntidadPersistente;
import domain.notificaciones.Notificacion;
import domain.notificaciones.Suscriptor;
import domain.notificaciones.TipoDeNotificacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "contactos")
@Entity
public class Contacto extends PersistenceEntity implements Suscriptor  {

  public Contacto(){}

  @Column(name = "email", unique = true, nullable = true)
  private String email = null;

  @Column(name = "whatsapp", unique = true, nullable = true)
  private String whatsApp = null;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "medios_notificacion")
  private List<Notificacion> mediosDeNotificacion = new ArrayList<>();

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

  public void agregarMedioDeNotificacion(Notificacion notificacion) {
    this.validarQuePuedeRecibirNotificacion(notificacion);
    this.mediosDeNotificacion.add(notificacion);
  }

  private void validarQuePuedeRecibirNotificacion(Notificacion notificacion) {
    if (this.email == null && notificacion.getTipoDeNotificacion() == TipoDeNotificacion.EMAIL) {
      throw new RuntimeException("El contacto no tiene un email asociado");
    }
    if (this.whatsApp == null && notificacion.getTipoDeNotificacion() == TipoDeNotificacion.WHATSAPP) {
      throw new RuntimeException("El contacto no tiene un número de WhatsApp asociado");
    }
  }

  public List<Notificacion> getMediosDeNotificacion() {
    return mediosDeNotificacion;
  }

}
