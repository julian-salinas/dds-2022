package domain.repositorios;

import domain.database.EntityManagerHelper;
import domain.notificaciones.TipoDeNotificacion;
import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.hc.HC;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;
import domain.ubicaciones.sectores.Municipio;
import domain.ubicaciones.sectores.Provincia;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /*
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
        String query =
                "FROM Organizacion " +
                "INNER JOIN provincias " +
                "ON municipios.provincia_id = provincias.id " +
                "WHERE provincias.id = " + provincia.getId();
        List<Organizacion> organizaciones = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
        return organizaciones;
    } */

    public List<Organizacion> inMunicipio(Municipio municipio) {
        List<Organizacion> organizaciones = this.dao.all();
        return organizaciones
                .stream()
                .filter(org -> org.sectorMunicipio().equals(municipio) ||
                        (org.sectorMunicipio().getId() == municipio.getId() &&
                                Objects.equals(org.sectorMunicipio().getNombre(), municipio.getNombre()))
                )
                .collect(Collectors.toList());
    }

    public List<Organizacion> inProvincia(Provincia provincia) {
        List<Organizacion> organizaciones = this.dao.all();
        return organizaciones
                .stream()
                .filter(org -> org.sectorProvincia().equals(provincia) ||
                        (org.sectorProvincia().getId() == provincia.getId() &&
                                Objects.equals(org.sectorProvincia().getNombre(), provincia.getNombre()))
                )
                .collect(Collectors.toList());
    }

    public double HCTotal(ClasificacionOrg clasificacionOrg) {
        // Lo de abajo no es igual a esto? (q esta aca arriba): List<Organizacion> organizaciones = this.dao.all();
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        CriteriaQuery query = EntityManagerHelper.getEntityManager().getCriteriaBuilder().createQuery(Organizacion.class);
        query.from(Organizacion.class);

        /*
            PREGUNTAR EL JUEVES 😎👌
         */
        List<Organizacion> organizaciones = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();

        return organizaciones.stream()
                .filter(organizacion -> organizacion.getClasificacion() == clasificacionOrg)
                .mapToDouble(organizacion -> organizacion.hcMensual().enKgCO2())
                .sum();
    }

    /*
    public List<HC> evolucionHC(int id) {
        return this.get(id).
    }*/
}
