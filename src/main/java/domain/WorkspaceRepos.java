package domain;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import domain.repositorios.RepositorioOrganizaciones;
import domain.ubicaciones.Ubicacion;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;
import java.util.List;

public class WorkspaceRepos {

  public static void main(String[] args) {

    // FULL CON REPOS

    Ubicacion ubicacion = new Ubicacion("rivadavia", 2000, "Localidad", null);
    Miembro miembro = new Miembro("Juan", "Carlos", TipoDeDocumento.DNI, 43567890);
    Sector sector = new Sector();
    sector.agregarMiembro(miembro);
    Organizacion organizacion = new Organizacion("S.A.", TipoOrganizacion.EMPRESA, "Panchos Loria", ubicacion, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    organizacion.agregarSector(sector);

    RepositorioOrganizaciones.getInstance().add(organizacion);

    Organizacion org = RepositorioOrganizaciones.getInstance().get(1);

    Sector sectorNuevo = new Sector();
    org.agregarSector(sectorNuevo);

    RepositorioOrganizaciones.getInstance().update(org);

    EntityManager em = PerThreadEntityManagers.getEntityManager();
    em.getTransaction().begin();
    List<Sector> listSectores = em.createQuery("from Sector").getResultList();
    em.getTransaction().commit();


    System.out.println(RepositorioOrganizaciones.getInstance().get(1).containsSector(listSectores.get(1)));

    Organizacion org1 = new Organizacion();
    Organizacion org2 = new Organizacion();
    Organizacion org3 = new Organizacion();
    Organizacion org4 = new Organizacion();
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);
    RepositorioOrganizaciones.getInstance().add(org4);

    RepositorioOrganizaciones.getInstance().clean();

    Organizacion org5 = new Organizacion();

    // Es la unica que aparece en la db, pero con id=6.
    RepositorioOrganizaciones.getInstance().add(org5);

  }

}
