package domain.repositorios;

import domain.notificaciones.Notificacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RepoNotificaciones {
  private static RepoNotificaciones instancia = null;
  private List<Notificacion> notificaciones = new ArrayList<>();

  public static RepoNotificaciones getInstancia() {
    if (instancia == null) {
      instancia = new RepoNotificaciones();
    }
    return instancia;
  }

  public List<Notificacion> getNotificaciones() {
    return this.notificaciones;
  }

  public void agregar(Notificacion ... notificaciones) {
    Collections.addAll(this.notificaciones, notificaciones);
  }

  public void eliminar(Notificacion notificacion) {
    this.notificaciones = this.notificaciones.stream()
        .filter(algunaNotificacion -> algunaNotificacion.equals(notificacion)).collect(Collectors.toList());
  }
}
