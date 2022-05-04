
import static org.junit.jupiter.api.Assertions.*;

import administrador.contrasenia.*;
import administrador.contrasenia.excepciones.*;
//import administrador.contrasenia.ValidacionLongitud;
//import administrador.contrasenia.excepciones.ExcepcionLongitudContrasenia;
import dominio.organizacionymiembros.Miembro;
import dominio.organizacionymiembros.Organizacion;
import dominio.organizacionymiembros.Sector;
import dominio.organizacionymiembros.TipoOrganizacion;

import dominio.trayecto.Tramo;
import dominio.trayecto.Trayecto;
import dominio.trayecto.transporte.Linea;
import dominio.trayecto.transporte.Parada;
import dominio.trayecto.transporte.TipoTransportePublico;
import dominio.trayecto.transporte.TransportePublico;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;

public class ValidacionTest {
  Organizacion organizacionDefault = new Organizacion("?", TipoOrganizacion.EMPRESA, "Rosario", "Ministerio");
  Sector sectorDefault = new Sector();
  Miembro miembroDefault = new Miembro("Juan", "Martin", "Crack?", "43-208-556");

  Trayecto trayectoDefault = new Trayecto();
  Parada paradaDefault1 = new Parada("Carabobo");
  Parada paradaDefault2 = new Parada("Puan");
  List<Parada> paradasLineaA = new ArrayList<>();
  // En los tests agregar paradaDefault1 y paradaDefault2 a paradasLineaA. (si se piensa usar)
  Linea lineaDefault = new Linea("A", paradasLineaA, TipoTransportePublico.SUBTE);
  TransportePublico subteLineaA = new TransportePublico(TipoTransportePublico.SUBTE, lineaDefault, paradaDefault1, paradaDefault2);
  Tramo tramoTransportePublico = new Tramo(subteLineaA);
  // En los tests agregar tramoTransportePublico a trayectoDefault. (si se piensa usar)

  // sumar tramos de otros tipos de transporte.


  // Tests generales

  @Test
  public void unMiembroSePuedeVincularAunaOrgYestaLoPuedeAceptar(){
    // Test principal
  }


  // Tests contraseña

  @Test
  public void dragonballzNoEsUnaClaveSegura() {
    ValidacionContraseniaComun contraseniaComun = new ValidacionContraseniaComun();
    assertThrows(ExcepcionContraseniaComun.class, () -> contraseniaComun.validarContrasenia("dragonballz"));
  }

  @Test
  public void messiEsUnaContraseniaMuyCorta() {
    ValidacionLongitud contraseniaCorta = new ValidacionLongitud();
    assertThrows(ExcepcionLongitudContrasenia.class, () -> contraseniaCorta.validarContrasenia("messi"));
  }

}
