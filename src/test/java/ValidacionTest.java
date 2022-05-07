
import static org.junit.jupiter.api.Assertions.*;

import administrador.contrasenia.*;
import administrador.contrasenia.excepciones.*;

import dominio.organizacionymiembros.*;

import dominio.organizacionymiembros.excepcionesOrgMiembros.ClasificacionOrganizacion;
import dominio.organizacionymiembros.excepcionesOrgMiembros.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import dominio.organizacionymiembros.excepcionesOrgMiembros.ExcepcionNoExisteElSectorEnLaOrganizacion;
import dominio.trayecto.Trayecto;
import dominio.trayecto.transporte.Linea;
import dominio.trayecto.transporte.Parada;
import dominio.trayecto.transporte.TipoTransportePublico;
import dominio.trayecto.transporte.TransportePublico;

import dominio.trayecto.transporte.excepcionesTransporte.ExcepcionParadasTransporteNoIncluidasEnLinea;
import dominio.trayecto.transporte.excepcionesTransporte.ExcepcionTipoTransporteNoIgualAtipoDeLinea;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidacionTest {
  ClasificacionOrganizacion ministerio = new ClasificacionOrganizacion("ministerio");
  ClasificacionOrganizacion universidad = new ClasificacionOrganizacion("universidad");
  ClasificacionOrganizacion escuela = new ClasificacionOrganizacion("escuela");

  Organizacion organizacionDefault = new Organizacion("?", TipoOrganizacion.EMPRESA, "Rosario", ministerio);

  Sector sectorDefault = new Sector();
  Sector sectorParaAgregar = new Sector();
  Miembro miembroDefault = new Miembro("Juan", "Martin", "Crack?", "43-208-556");
  Miembro miembroError = new Miembro("Pedro", "Gonzales", "Crack?", "43-218-556");

  Trayecto trayectoDefault = new Trayecto();
  Parada paradaDefault1 = new Parada("Carabobo");
  Parada paradaDefault2 = new Parada("Puan");
  List<Parada> paradasLineaA = new ArrayList<>();
  // En los tests agregar paradaDefault1 y paradaDefault2 a paradasLineaA. (si se piensa usar)
  Linea lineaDefault = new Linea("A", paradasLineaA, TipoTransportePublico.SUBTE);
  //Tramo tramoTransportePublico = new Tramo(subteLineaA);
  // En los tests agregar tramoTransportePublico a trayectoDefault. (si se piensa usar)

  // sumar tramos de otros tipos de transporte.


  // Tests generales

  @Test
  public void unMiembroSePuedeVincularAunaOrgYestaLoPuedeAceptar(){
    // Test principal
    organizacionDefault.agregarSector(sectorDefault);
    miembroDefault.vincularTrabajadorConOrg(organizacionDefault,sectorDefault);
    organizacionDefault.aceptarVinculacionDeTrabajador(miembroDefault);
    assertTrue(sectorDefault.getListaDeMiembros().contains(miembroDefault));
  }

  @Test
  public void unMiembroNoSePuedeVincularAunSectorQueNoExisteEnLaOrg(){
    // Test principal
    // Existe la org y el sector, sin embargo el sector NO es un sector de la org.
    assertThrows(ExcepcionNoExisteElSectorEnLaOrganizacion.class,
        () -> miembroDefault.vincularTrabajadorConOrg(organizacionDefault,sectorDefault));
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

  @Test
  public void agregoParadaAUnaLinea(){
    lineaDefault.agregarParada(paradaDefault1);
    assertTrue(lineaDefault.getParadas().contains(paradaDefault1));
  }

  @Test
  public void puedoCrearUnTransportePublicoSiSuTipoYparadasCoincidenConLosDeLaLinea(){
    lineaDefault.agregarParada(paradaDefault1);
    lineaDefault.agregarParada(paradaDefault2);
    assertDoesNotThrow(() -> new TransportePublico(TipoTransportePublico.SUBTE, lineaDefault, paradaDefault1, paradaDefault2));
  }

  @Test
  public void crearUnTransportePublicoCuyoTipoYtipoDeLineaNoCoincidenTiraError(){
    // lineaDefault.tipo es SUBTE
    lineaDefault.agregarParada(paradaDefault1);
    lineaDefault.agregarParada(paradaDefault2);
    assertThrows(ExcepcionTipoTransporteNoIgualAtipoDeLinea.class, () -> new TransportePublico(TipoTransportePublico.TREN, lineaDefault, paradaDefault1, paradaDefault2));
  }

  @Test
  public void crearUnTransportePublicoCuyasParadasNoEstenEnLaLineaTiraError(){
    lineaDefault.agregarParada(paradaDefault1);
    lineaDefault.agregarParada(paradaDefault2);
    Parada paradaError1 = new Parada("Acoyte");
    Parada paradaError2 = new Parada("Castro Barros");
    // Nunca los agrego a lineaDefault
    assertThrows(ExcepcionParadasTransporteNoIncluidasEnLinea.class, () -> new TransportePublico(TipoTransportePublico.SUBTE, lineaDefault, paradaError1, paradaError2));
  }

  // Tests contraseña

  @Test
  public void validacionContraseniaComunTest() {
    ValidacionContraseniaComun contraseniaComun = new ValidacionContraseniaComun();
    assertThrows(ExcepcionContraseniaComun.class, () -> contraseniaComun.validarContrasenia("dragonballz"));
  }

  @Test
  public void validacionLongitudTest() {
    ValidacionLongitud contraseniaCorta = new ValidacionLongitud();
    assertThrows(ExcepcionLongitudContrasenia.class, () -> contraseniaCorta.validarContrasenia("messi"));
  }

  @Test
  public void testValidacionMinusculas() {
    ValidacionMinusculas contraseniaSinMinusculas = new ValidacionMinusculas();
    assertThrows(ExcepcionContraseniaNoContieneMinusculas.class, () -> contraseniaSinMinusculas.validarContrasenia("TODOMAYUSCULA"));
  }

  @Test
  public void testValidacionMayusculas() {
    ValidacionMayusculas contraseniaSinMayusculas = new ValidacionMayusculas();
    assertThrows(ExcepcionContraseniaNoContieneMayusculas.class, () -> contraseniaSinMayusculas.validarContrasenia("todominuscula"));
  }

  @Test
  public void testValidacionNumeros() {
    ValidacionNumeros contraseniaQueNoTieneNumeros = new ValidacionNumeros();
    assertThrows(ExcepcionContraseniaEsNumerica.class, () -> contraseniaQueNoTieneNumeros.validarContrasenia("hola"));
  }

}
