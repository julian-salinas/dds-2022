package domain.organizaciones;

import domain.organizaciones.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.organizaciones.excepciones.ExcepcionNoExisteElSectorEnLaOrganizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import domain.ubicaciones.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VinculacionesTests {
  Organizacion organizacionDefault;
  Sector sectorDefault;
  Miembro miembroDefault;
  Ubicacion ubicacionDefault;

  @BeforeEach
  void init() {
    ubicacionDefault = new Ubicacion();
    organizacionDefault = new Organizacion("Mc", "S.A.", TipoOrganizacion.EMPRESA ,ubicacionDefault, ClasificacionOrg.MINISTERIO);
    sectorDefault = new Sector();
    miembroDefault = new Miembro("Juan", "Martin", TipoDeDocumento.DNI, 43208556);
  }

  @Test
  public void unMiembroSePuedeVincularAunaOrganizacion(){
    organizacionDefault.agregarSector(sectorDefault);
    miembroDefault.vincularTrabajadorConOrg(organizacionDefault, sectorDefault);
    assertTrue(sectorDefault.containsMiembroParaAceptar(miembroDefault));
    assertFalse(sectorDefault.containsMiembro(miembroDefault));
  }

  @Test
  public void unMiembroSePuedeVincularAunaOrganizacionYestaLoPuedeAceptar(){
    organizacionDefault.agregarSector(sectorDefault);
    miembroDefault.vincularTrabajadorConOrg(organizacionDefault, sectorDefault);
    organizacionDefault.aceptarVinculacionDeTrabajador(miembroDefault, sectorDefault);
    assertFalse(sectorDefault.containsMiembroParaAceptar(miembroDefault));
    assertTrue(sectorDefault.containsMiembro(miembroDefault));
    //assertEquals(miembroDefault.getSectorDondeTrabaja(), sectorDefault);
  }

  @Test
  public void unMiembroNoSePuedeVincularAunSectorQueNoExisteEnLaOrganizacion(){
    // Existe la org y el sector, sin embargo el sector NO es un sector de la org.
    assertThrows(ExcepcionNoExisteElSectorEnLaOrganizacion.class,
        () -> miembroDefault.vincularTrabajadorConOrg(organizacionDefault,sectorDefault));
  }

  @Test
  public void unMiembroNoSePuedeAceptarUnaVinculacionCuyoSectorNoExisteEnLaOrganizacion(){
    // Existe la org y el sector, sin embargo el sector NO es un sector de la org.
    assertThrows(ExcepcionNoExisteElSectorEnLaOrganizacion.class,
        () -> organizacionDefault.aceptarVinculacionDeTrabajador(miembroDefault, sectorDefault));
  }

  @Test
  public void noSePuedeAceptarAunMiembroEnUnaOrgSiEsteNoPidioVincularse(){
    // Existe la org y el miembro, sin embargo el miembro NUNCA pidio vincularse a la org.
    organizacionDefault.agregarSector(sectorDefault);
    assertThrows(ExcepcionNoExisteElMiembroAacptarEnLaOrg.class,
        () -> organizacionDefault.aceptarVinculacionDeTrabajador(miembroDefault, sectorDefault));
  }

}
