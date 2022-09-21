package domain.notificaciones;

import domain.notificaciones.adapters.EmailAdapter;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("email")
public class NotificacionEmail extends Notificacion {
    public NotificacionEmail(String sendgridApiKey, String email) {
        super(new EmailAdapter(sendgridApiKey, email), TipoDeNotificacion.EMAIL);
    }
}