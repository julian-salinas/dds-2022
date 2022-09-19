package domain.repositorios;

import domain.notificaciones.contactos.Contacto;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAO;

public class RepositorioContactos extends Repositorio<Contacto> {
    public RepositorioContactos(DAO<Contacto> dao) {
        super(dao);
    }
}
