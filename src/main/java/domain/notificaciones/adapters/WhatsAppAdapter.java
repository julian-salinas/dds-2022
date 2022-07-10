package domain.notificaciones.adapters;

import com.twilio.Twilio;
import domain.notificaciones.NotificacionAdapter;
import com.twilio.rest.api.v2010.account.Message;
import domain.organizaciones.Contacto;

public class WhatsAppAdapter implements NotificacionAdapter {

  public static final String ACCOUNT_SID = "ACd49ec824fc3760b72ae3c0fbbfcf3d31";
  public static final String AUTH_TOKEN = "fb6e2691c4ed683f08884bf72c43cbea";
  public static final String TWILIO_PHONE_NUMBER = "whatsapp:+14155238886";

  public WhatsAppAdapter() {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
  }

  public void enviar(Contacto contacto, String mensaje) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(new com.twilio.type.PhoneNumber("whatsapp" + contacto.getWhatsApp()),  // Ejemplo: "whatsapp:+5491152573517"
                                      new com.twilio.type.PhoneNumber(this.TWILIO_PHONE_NUMBER),
                                      mensaje).create();
  }
}
