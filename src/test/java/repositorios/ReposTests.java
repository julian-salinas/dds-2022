package repositorios;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import domain.repositorios.RepositorioOrganizaciones;
import domain.ubicaciones.Ubicacion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReposTests {

  // Nota: Los ID's empiezan en 1.

  @Test
  public void sePuedeGuardarUnaOrg() {

    Ubicacion ubicacion = new Ubicacion("rivadavia", 2000, "Localidad", null);
    Miembro miembro = new Miembro("Juan", "Carlos", TipoDeDocumento.DNI, 43567890);
    Sector sector = new Sector();
    sector.agregarMiembro(miembro);
    Organizacion organizacion = new Organizacion("S.A.", TipoOrganizacion.EMPRESA, "Panchos Loria", ubicacion, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    organizacion.agregarSector(sector);

    RepositorioOrganizaciones.getInstance().add(organizacion);

    assertEquals(organizacion, RepositorioOrganizaciones.getInstance().get(1));

  }

}
