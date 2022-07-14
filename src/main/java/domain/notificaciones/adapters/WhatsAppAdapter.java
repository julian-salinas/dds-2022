package domain.notificaciones.adapters;

import com.twilio.Twilio;
import domain.notificaciones.NotificacionAdapter;
import com.twilio.rest.api.v2010.account.Message;
import domain.organizaciones.Contacto;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WhatsAppAdapter implements NotificacionAdapter {

  private String ACCOUNT_SID;
  private String AUTH_TOKEN;
  private String TWILIO_PHONE_NUMBER;

  public WhatsAppAdapter() {
    // set apiKey value from local.properties
    try{
      InputStream input = new FileInputStream("src/main/java/domain/local.properties");
      Properties properties = new Properties();
      properties.load(input);
      this.ACCOUNT_SID = properties.getProperty("TWILIO_ACCOUNT_SID");;
      this.AUTH_TOKEN = properties.getProperty("TWILIO_AUTH_TOKEN");
      this.TWILIO_PHONE_NUMBER = properties.getProperty("TWILIO_PHONE_NUMBER");
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
  }

  public int enviar(Contacto contacto, String mensaje) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(new com.twilio.type.PhoneNumber("whatsapp:+" + contacto.getWhatsApp()),  // Ejemplo: "whatsapp:+5491152573517"
                                      new com.twilio.type.PhoneNumber("whatsapp:+" + this.TWILIO_PHONE_NUMBER),
                                      mensaje).create();

    return message.getStatus().ordinal();
  }
}
