package repositorios;

import repositorios.daos.DAO;
import domain.notificaciones.Notificacion;
import repositorios.daos.DAOHibernate;

public class RepositorioNotificaciones extends Repositorio<Notificacion> {

    private static final RepositorioNotificaciones instance = null;

    private RepositorioNotificaciones(DAO<Notificacion> dao) {
        super(dao);
    }

    public static RepositorioNotificaciones getInstance() {
        return new RepositorioNotificaciones(new DAOHibernate<>(Notificacion.class));
    }
}
