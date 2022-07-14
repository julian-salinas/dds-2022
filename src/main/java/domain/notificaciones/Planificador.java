package domain.notificaciones;

import org.quartz.*;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

public class Planificador {

  public static void main(String args[]) throws SchedulerException {

    SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();

    Scheduler scheduler = schedulerFactory.getScheduler();

    CronTrigger trigger = newTrigger()
        .withIdentity("trigger1", "group1")
        .withSchedule(cronSchedule("0/6 * * * * ? *")) // Cada 6 segundos
        //.withSchedule(cronSchedule("0 0 12 1 1/1 ? *")) // Cada primer día del mes - 12:00 hs
        .forJob("job1", "group1")
        .startNow()
        .build();

    JobDetail jobDetail = newJob(ObserverNotificaciones.class).withIdentity("job1", "group1").build();

    scheduler.start();
    scheduler.scheduleJob(jobDetail, trigger);

    System.out.println("Probando planificador...");

    try {
      Thread.sleep(5L * 1000L);
      System.out.println("5s transcurridos");
      Thread.sleep(5L * 1000L);
      System.out.println("10s transcurridos");
      Thread.sleep(5L * 1000L);
      System.out.println("15s transcurridos");

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    scheduler.shutdown(true);
    System.out.println("Apagando planificador...");
  }
}
