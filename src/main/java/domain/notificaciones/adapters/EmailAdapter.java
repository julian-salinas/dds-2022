package domain.notificaciones.adapters;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import domain.notificaciones.NotificacionAdapter;
import domain.organizaciones.Contacto;

import java.io.IOException;

public class EmailAdapter implements NotificacionAdapter {

  private static final String SENDGRID_API_KEY = "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";
  private static final String OWN_EMAIL = "un.mail.muy.trucho@gmail.com";
  private SendGrid sendGrid;

  public EmailAdapter() {
    this.sendGrid = new SendGrid("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");
  }

  public void enviar(Contacto contacto, String mensaje) {
    Mail mail = this.generarMail(this.OWN_EMAIL, "DDS - TPA#3", contacto.getEmail(), mensaje);

    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = this.sendGrid.api(request);
    } catch (IOException exception) {
        throw new RuntimeException(exception.toString());
    }
  }

  private Mail generarMail(String from, String subject, String to, String content) {
    return new Mail(new Email(from), subject, new Email(to), new Content("text/plain", content));
  }

}
