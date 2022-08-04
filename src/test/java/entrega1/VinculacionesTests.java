package entrega1;

import domain.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.excepciones.ExcepcionNoExisteElSectorEnLaOrganizacion;
import domain.miembros.Miembro;
import domain.miembros.TipoDeDocumento;
import domain.organizaciones.ClasificacionOrganizacion;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.TipoOrganizacion;
import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.Ubicacion;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class VinculacionesTests {
  ServicioGeoDds apiClient;
  Organizacion organizacionDefault;
  Sector sectorDefault;
  Miembro miembroDefault;
  ClasificacionOrganizacion ministerio;
  Ubicacion ubicacionDefault;

  @BeforeEach
  void init() throws IOException {
    apiClient = mock(ServicioGeoDds.class);
    when(apiClient.verificarNombreLocalidad(anyString())).thenReturn(2);  //id Localidad = 2
    when(apiClient.nombreMunicipio(2)).thenReturn("Valcheta");
    when(apiClient.verificarNombreMunicipio("Valcheta")).thenReturn(4);   //id Municipio = 4
    when(apiClient.nombreProvincia(4)).thenReturn("Rio Negro");
    when(apiClient.verificarNombreProvincia("Rio Negro")).thenReturn(7);  //id Provincia = 7

    try {
      ubicacionDefault = new Ubicacion("Corrientes", 1200, "PUERTO LEONI", apiClient);
    } catch (IOException e) {
      e.printStackTrace();
    }
    ministerio = new ClasificacionOrganizacion("ministerio");

    organizacionDefault = new Organizacion("S.A.", TipoOrganizacion.EMPRESA, "Mc" ,ubicacionDefault, ministerio);
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
    assertEquals(miembroDefault.getSectorDondeTrabaja(), sectorDefault);
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
