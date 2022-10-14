package domain;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import domain.repositorios.RepositorioOrganizaciones;
import domain.ubicaciones.Ubicacion;

public class WorkspaceRepos {

  public static void main(String[] args) {

    // TODO CON REPOS

    Ubicacion ubicacion = new Ubicacion("rivadavia", 2000, "Localidad", null);
    Miembro miembro = new Miembro("Juan", "Carlos", TipoDeDocumento.DNI, 43567890);
    Sector sector = new Sector();
    sector.agregarMiembro(miembro);
    Organizacion organizacion = new Organizacion("S.A.", TipoOrganizacion.EMPRESA, "Panchos Loria", ubicacion, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    organizacion.agregarSector(sector);

    RepositorioOrganizaciones.getInstance().add(organizacion);

    Organizacion organizacion1 = RepositorioOrganizaciones.getInstance().get(1);

    System.out.println(organizacion.equals(organizacion1));

  }

}
