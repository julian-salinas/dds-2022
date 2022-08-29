package domain.notificaciones.adapters;

import com.twilio.Twilio;
import domain.notificaciones.MensajeriaAdapter;
import com.twilio.rest.api.v2010.account.Message;
import domain.notificaciones.Suscriptor;

public class WhatsAppAdapter implements MensajeriaAdapter {
  private String ACCOUNT_SID;
  private String AUTH_TOKEN;
  private String TWILIO_PHONE_NUMBER;

  public WhatsAppAdapter(String accountSID, String authToken, String twilioPhoneNumber) {
    this.ACCOUNT_SID = accountSID;
    this.AUTH_TOKEN = authToken;
    this.TWILIO_PHONE_NUMBER = twilioPhoneNumber;  // 5491152573517
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
  }

  /**
   * @name: enviar
   * @param suscriptor: Contacto al cual se enviará la notificación mediante todas sus suscripciones
   * @param mensaje: Mensaje a enviar
   * @return: status code del request realizado
   */
  public int enviar(Suscriptor suscriptor, String mensaje) {
    Message message = Message.creator(new com.twilio.type.PhoneNumber("whatsapp:+" + suscriptor.getWhatsApp()),
                                      new com.twilio.type.PhoneNumber("whatsapp:" + this.TWILIO_PHONE_NUMBER),
                                      mensaje).create();

    return message.getStatus().ordinal();
  }
}
