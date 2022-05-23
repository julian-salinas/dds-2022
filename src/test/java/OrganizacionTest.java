import domain.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.miembros.ClasificacionOrganizacion;
import domain.miembros.Miembro;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.TipoOrganizacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizacionTest {

  private Organizacion organizacionDefault;
  private Sector sectorDefault;
  private Miembro miembroDefault;
  private ClasificacionOrganizacion ministerio;

  @BeforeEach
  void init() {
    ministerio = new ClasificacionOrganizacion("ministerio");

    organizacionDefault = new Organizacion("?", TipoOrganizacion.EMPRESA, "Rosario", ministerio);

    sectorDefault = new Sector();

    miembroDefault = new Miembro("Juan", "Martin", "Crack?", "43-208-556");

  }

  @Test
  public void noSePuedeAceptarAunMiembroEnUnaOrgSiEsteNoPidioVincularse(){
    // Test principal
    // Existe la org y el miembro, sin embargo el miembro NUNCA pidio vincularse a la org.
    assertThrows(ExcepcionNoExisteElMiembroAacptarEnLaOrg.class,
        () -> organizacionDefault.aceptarVinculacionDeTrabajador(miembroDefault));
  }

  @Test
  public void sePuedeAgregarUnSectorAUnaOrganizacion(){
    organizacionDefault.agregarSector(sectorDefault);
    assertTrue(organizacionDefault.getSectores().contains(sectorDefault));
  }
}
