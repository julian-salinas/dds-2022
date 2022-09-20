package domain.repositorios;

import domain.database.EntityManagerHelper;
import domain.organizaciones.Organizacion;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;
import domain.ubicaciones.sectores.Municipio;
import domain.ubicaciones.sectores.Provincia;

import java.util.List;

public class RepositorioOrganizaciones extends Repositorio<Organizacion> {

    private static RepositorioOrganizaciones instance = null;

    private RepositorioOrganizaciones(DAO<Organizacion> dao) {
        super(dao);
    }

    public static RepositorioOrganizaciones getInstance() {
        if (instance == null) {
            instance = new RepositorioOrganizaciones(new DAOHibernate<>(Organizacion.class));
        }
        return instance;
    }

    public List<Organizacion> inMunicipio(Municipio municipio) {
        String query = "SELECT * " +
                "FROM organizaciones " +
                "INNER JOIN municipios " +
                "ON organizaciones.municipio_id = municipios.id " +
                "WHERE municipios.id = " + municipio.getId();
        List<Organizacion> organizaciones = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
        return organizaciones;
    }

    public List<Organizacion> inProvincia(Provincia provincia) {
        String query = "SELECT * " +
                "FROM organizaciones " +
                "INNER JOIN provincias " +
                "ON municipios.provincia_id = provincias.id " +
                "WHERE provincias.id = " + provincia.getId();
        List<Organizacion> organizaciones = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
        return organizaciones;
    }
}
