package domain.notificaciones;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class ObserverNotificaciones implements Job {
  private Notificador notificador = Notificador.getInstancia();

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
    this.notificador.notificarATodos();
  }
}
