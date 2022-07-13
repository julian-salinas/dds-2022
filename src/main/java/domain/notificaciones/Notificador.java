package domain.notificaciones;

import domain.organizaciones.Contacto;
import domain.organizaciones.Organizacion;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Notificador implements Job {
  private Organizacion organizacion;

  public Notificador(Organizacion organizacion) {
    this.organizacion = organizacion;
  }

  public void notificarAUnContacto(Contacto contacto) {
    contacto.getSuscripciones().forEach(notificacion -> notificacion.enviar(contacto));
  }

  public void notificarContactosDeOrganizacion() {
    this.organizacion.getContactos().forEach(contacto -> notificarAUnContacto(contacto));
  }

  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
    this.notificarContactosDeOrganizacion();
  }

  public void iniciar() throws SchedulerException {
    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.start();

    JobDetail job = newJob(Notificador.class).withIdentity("Notificacion").build();

    // La notificacion se va a enviar el día 1 de cada mes a todos los contactos de la organizacion
    CronScheduleBuilder interval = CronScheduleBuilder.monthlyOnDayAndHourAndMinute(1, 0, 0);

    Trigger trigger = TriggerBuilder.newTrigger()
                                    .withIdentity("Trigger mensual")
                                    .withSchedule(interval).build();

    scheduler.scheduleJob(job, trigger);
  }
}
