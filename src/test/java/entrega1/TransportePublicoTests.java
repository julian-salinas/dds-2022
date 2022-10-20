package entrega1;

import domain.trayecto.transporte.publico.Linea;
import domain.trayecto.transporte.publico.Parada;
import domain.trayecto.transporte.publico.TipoTransportePublico;
import domain.trayecto.transporte.publico.TransportePublico;
import domain.trayecto.transporte.excepciones.ExcepcionParadasTransporteNoIncluidasEnLinea;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.distancia.UnidadDistancia;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransportePublicoTests {

  Ubicacion ubicacionSanP;
  Ubicacion ubicacionPdM;
  Parada    paradaSanP;
  Parada    paradaPdM;
  Linea     lineaA;

  @BeforeEach
  public void init() {
    ubicacionSanP   = new Ubicacion("Av. Rivadavia", 200, null, null);
    ubicacionPdM    = new Ubicacion("Av. Rivadavia", 5000, null, null);
    paradaSanP      = new Parada("San Pedrito",
                                          ubicacionSanP,
                                          new Distancia(250.0, UnidadDistancia.MTS));
    paradaPdM       = new Parada("Plaza De Mayo",
                                          ubicacionPdM,
                                          new Distancia(0.0, UnidadDistancia.MTS));

    lineaA = new Linea("A");
    lineaA.agregarParada(paradaSanP);
    lineaA.agregarParada(paradaPdM);
  }

  @Test
  public void noPuedoCrearUnTramoEntreParadasQNoFormenParteDelTransportePublicoEnCuestion() {
    Ubicacion ubicacionError = new Ubicacion("Av. Carabobo", 3700, null, null);
    Parada paradaError1 = new Parada("Av. Carabobo, 3700",
        ubicacionError,
        new Distancia(700, UnidadDistancia.MTS));

    assertThrows(ExcepcionParadasTransporteNoIncluidasEnLinea.class, () -> lineaA.findParada(ubicacionError));
  }

}
