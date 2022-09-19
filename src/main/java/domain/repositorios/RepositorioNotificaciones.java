package domain.repositorios;

import domain.repositorios.daos.DAO;
import domain.notificaciones.Notificacion;

public class RepositorioNotificaciones extends Repositorio<Notificacion> {
    public RepositorioNotificaciones(DAO<Notificacion> dao) {
        super(dao);
    }
}
