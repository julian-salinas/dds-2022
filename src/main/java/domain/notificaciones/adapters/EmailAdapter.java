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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailAdapter implements NotificacionAdapter {

  private String SENDGRID_API_KEY;;
  private String OWN_EMAIL;
  private SendGrid sendGrid;

  public EmailAdapter() {
    // set apiKey value from local.properties
    try{
      InputStream input = new FileInputStream("src/main/java/domain/local.properties");
      Properties properties = new Properties();
      properties.load(input);
      this.SENDGRID_API_KEY = properties.getProperty("SENDGRID_API_KEY");;
      this.OWN_EMAIL = properties.getProperty("OWN_EMAIL");
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    this.sendGrid = new SendGrid(this.SENDGRID_API_KEY);
  }

  public int enviar(Contacto contacto, String mensaje) {
    Mail mail = this.generarMail(this.OWN_EMAIL, "DDS - TPA#3", contacto.getEmail(), mensaje);

    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = this.sendGrid.api(request);
      return response.getStatusCode();

    } catch (IOException exception) {
        exception.printStackTrace();
    }

    return -1;
  }

  private Mail generarMail(String from, String subject, String to, String content) {
    return new Mail(new Email(from), subject, new Email(to), new Content("text/plain", content));
  }

}
