import domain.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.organizaciones.ClasificacionOrganizacion;
import domain.miembros.Miembro;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.TipoOrganizacion;
import domain.ubicaciones.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizacionTest {

  private Organizacion organizacionDefault;
  private Sector sectorDefault;
  private Miembro miembroDefault;
  private ClasificacionOrganizacion ministerio;
  private Ubicacion ubicacionDefault;

  @BeforeEach
  void init() {
    try {
      ubicacionDefault = new Ubicacion("Corrientes", 1200, "PUERTO LEONI ");
    } catch (IOException e) {
      //xd
    }
    ministerio = new ClasificacionOrganizacion("ministerio");
    organizacionDefault = new Organizacion("?", TipoOrganizacion.EMPRESA, ubicacionDefault, ministerio);
    sectorDefault = new Sector();
    miembroDefault = new Miembro("Juan", "Martin", "Crack?", "43-208-556");
  }

  @Test
  public void noSePuedeAceptarAunMiembroEnUnaOrgSiEsteNoPidioVincularse(){
    // Existe la org y el miembro, sin embargo el miembro NUNCA pidio vincularse a la org.
    assertThrows(ExcepcionNoExisteElMiembroAacptarEnLaOrg.class,
        () -> organizacionDefault.aceptarVinculacionDeTrabajador(miembroDefault, sectorDefault));
  }

  @Test
  public void sePuedeAgregarUnSectorAUnaOrganizacion(){
    organizacionDefault.agregarSector(sectorDefault);
    assertTrue(organizacionDefault.containsSector(sectorDefault));
    assertEquals(sectorDefault.getOrgAlaQuePertenezco(), organizacionDefault);
  }
}
