package domain.notificaciones.adapters;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import domain.notificaciones.MensajeriaAdapter;
import domain.notificaciones.Suscriptor;
import java.io.IOException;


public class EmailAdapter implements MensajeriaAdapter {
  private String SENDGRID_API_KEY;;
  private String EMAIL;
  private SendGrid sendGrid;

  public EmailAdapter(String sendgridApiKey, String email) {
    this.SENDGRID_API_KEY = sendgridApiKey;
    this.EMAIL = email;
    this.sendGrid = new SendGrid(this.SENDGRID_API_KEY);
  }

  /**
   * @name: enviar
   * @param suscriptor: Contacto al cual se enviará la notificación mediante todas sus suscripciones
   * @param mensaje: Mensaje a enviar
   * @return
   */
  public int enviar(Suscriptor suscriptor, String mensaje) {
    Mail mail = this.generarMail(this.EMAIL, "DDS - TPA#3", suscriptor.getEmail(), mensaje);
    Request request = new Request();

    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = this.sendGrid.api(request);
      return response.getStatusCode();
    }
    catch (IOException exception) {
        exception.printStackTrace();
        throw new RuntimeException("Error al enviar email");
    }
  }

  private Mail generarMail(String from, String subject, String to, String content) {
    return new Mail(new Email(from), subject, new Email(to), new Content("text/plain", content));
  }

}
