import domain.trayecto.Trayecto;
import domain.trayecto.transporte.Linea;
import domain.trayecto.transporte.Parada;
import domain.trayecto.transporte.TipoTransportePublico;
import domain.trayecto.transporte.TransportePublico;
import domain.trayecto.transporte.excepciones.ExcepcionParadasTransporteNoIncluidasEnLinea;
import domain.trayecto.transporte.excepciones.ExcepcionTipoTransporteNoIgualAtipoDeLinea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrayectoTest {

  Linea lineaDefault;
  Parada paradaDefault1, paradaDefault2;
  Trayecto trayectoDefault;
  List<Parada> paradasLineaA;

  @BeforeEach
  void init() {
    paradasLineaA = new ArrayList<>();

    lineaDefault = new Linea("A", paradasLineaA, TipoTransportePublico.SUBTE);

    paradaDefault1 = new Parada("Carabobo", 4);
    paradaDefault2 = new Parada("Puan", 5);

    trayectoDefault = new Trayecto();
  }

  @Test
  public void agregoParadaAUnaLinea(){
    lineaDefault.agregarParada(paradaDefault1);
    assertTrue(lineaDefault.containsParada(paradaDefault1));
  }

  @Test
  public void puedoCrearUnTransportePublicoSiSuTipoYparadasCoincidenConLosDeLaLinea(){
    lineaDefault.agregarParada(paradaDefault1);
    lineaDefault.agregarParada(paradaDefault2);
    assertDoesNotThrow(() -> new TransportePublico(TipoTransportePublico.SUBTE, lineaDefault, paradaDefault1, paradaDefault2));
  }

  @Test
  public void crearUnTransportePublicoCuyoTipoYtipoDeLineaNoCoincidenTiraError(){
    lineaDefault.agregarParada(paradaDefault1);
    lineaDefault.agregarParada(paradaDefault2);
    // lineaDefault.tipo es SUBTE
    // nuevoTransportePublico.tipo es TREN, aunque implementa lineaDefault
    assertThrows(ExcepcionTipoTransporteNoIgualAtipoDeLinea.class, () -> new TransportePublico(TipoTransportePublico.TREN, lineaDefault, paradaDefault1, paradaDefault2));
  }

  @Test
  public void crearUnTransportePublicoCuyasParadasNoEstenEnLaLineaTiraError(){
    lineaDefault.agregarParada(paradaDefault1);
    lineaDefault.agregarParada(paradaDefault2);
    Parada paradaError1 = new Parada("Acoyte", 7);
    Parada paradaError2 = new Parada("Castro Barros", 6);
    // Nunca los agrego a lineaDefault
    assertThrows(ExcepcionParadasTransporteNoIncluidasEnLinea.class, () -> new TransportePublico(TipoTransportePublico.SUBTE, lineaDefault, paradaError1, paradaError2));
  }
}
