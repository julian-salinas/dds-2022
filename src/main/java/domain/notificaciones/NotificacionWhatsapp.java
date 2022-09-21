package domain.notificaciones;

import domain.notificaciones.adapters.WhatsAppAdapter;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("whatsapp")
public class NotificacionWhatsapp extends Notificacion {
    public NotificacionWhatsapp(String accountSID, String authToken, String twilioPhoneNumber) {
        super(new WhatsAppAdapter(accountSID, authToken, twilioPhoneNumber), TipoDeNotificacion.WHATSAPP);
    }
}