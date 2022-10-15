package entrega1;

import domain.trayecto.transporte.publico.Linea;
import domain.trayecto.transporte.publico.Parada;
import domain.trayecto.transporte.publico.TipoTransportePublico;
import domain.trayecto.transporte.publico.TransportePublico;
import domain.trayecto.transporte.excepciones.ExcepcionParadasTransporteNoIncluidasEnLinea;
import domain.trayecto.transporte.excepciones.ExcepcionTipoTransporteNoIgualAtipoDeLinea;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.distancia.UnidadDistancia;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransportesTests {

  Linea lineaDefault;
  Parada paradaDefault1, paradaDefault2;
  List<Parada> paradasLineaA = new ArrayList<>();

  @BeforeEach
  public void init() {
    lineaDefault = new Linea("A", paradasLineaA, TipoTransportePublico.SUBTE);
    paradaDefault1 = new Parada("Carabobo", new Distancia(4, UnidadDistancia.KM));
    paradaDefault2 = new Parada("Puan", new Distancia(5, UnidadDistancia.KM));
  }

  @Test
  public void puedoCrearUnTransportePublicoSiSuTipoYparadasCoincidenConLosDeLaLinea() {
    lineaDefault.agregarParada(paradaDefault1);
    lineaDefault.agregarParada(paradaDefault2);
    assertDoesNotThrow(() -> new TransportePublico(TipoTransportePublico.SUBTE, lineaDefault, paradaDefault1, paradaDefault2));
  }

  @Test
  public void crearUnTransportePublicoCuyoTipoYtipoDeLineaNoCoincidenTiraError() {
    lineaDefault.agregarParada(paradaDefault1);
    lineaDefault.agregarParada(paradaDefault2);
    // lineaDefault.tipo es SUBTE
    // nuevoTransportePublico.tipo es TREN, aunque implementa lineaDefault
    assertThrows(ExcepcionTipoTransporteNoIgualAtipoDeLinea.class, () -> new TransportePublico(TipoTransportePublico.TREN, lineaDefault, paradaDefault1, paradaDefault2));
  }

  @Test
  public void crearUnTransportePublicoCuyasParadasNoEstenEnLaLineaTiraError() {
    lineaDefault.agregarParada(paradaDefault1);
    lineaDefault.agregarParada(paradaDefault2);
    Parada paradaError1 = new Parada("Acoyte", new Distancia(700, UnidadDistancia.MTS));
    Parada paradaError2 = new Parada("Castro Barros", new Distancia(6, UnidadDistancia.MTS));
    // Nunca los agrego a lineaDefault
    assertThrows(ExcepcionParadasTransporteNoIncluidasEnLinea.class, () -> new TransportePublico(TipoTransportePublico.SUBTE, lineaDefault, paradaError1, paradaError2));
  }

}
