package domain.repositorios;

import domain.database.EntityManagerHelper;
import domain.notificaciones.contactos.Contacto;
import domain.organizaciones.Organizacion;
import domain.repositorios.daos.DAO;
import domain.ubicaciones.sectores.Municipio;
import domain.ubicaciones.sectores.Provincia;

import java.util.List;

public class RepositorioOrganizaciones extends Repositorio<Organizacion> {
    public RepositorioOrganizaciones(DAO<Organizacion> dao) {
        super(dao);
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
                "INNER JOIN municipios " +
                "ON organizaciones.municipio_id = municipios.id " +
                "INNER JOIN provincias " +
                "ON municipios.provincia_id = provincias.id " +
                "WHERE provincias.id = " + provincia.getId();
        List<Organizacion> organizaciones = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
        return organizaciones;
    }

}
