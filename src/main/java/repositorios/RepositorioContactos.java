package repositorios;

import domain.notificaciones.contactos.Contacto;
import repositorios.daos.DAO;
import repositorios.daos.DAOHibernate;

public class RepositorioContactos extends Repositorio<Contacto> {
    private static final RepositorioContactos instance = null;

    private RepositorioContactos(DAO<Contacto> dao) {
        super(dao);
    }

    public static RepositorioContactos getInstance() {
        return new RepositorioContactos(new DAOHibernate<>(Contacto.class));
    }
}
