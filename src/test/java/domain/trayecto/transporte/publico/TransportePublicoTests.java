package domain.trayecto.transporte.publico;

import domain.trayecto.Tramo;
import domain.trayecto.transporte.excepciones.ExcepcionParadasTransporteNoIncluidasEnLinea;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.distancia.UnidadDistancia;
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
    ubicacionSanP   = new Ubicacion("Av. Rivadavia", 200);
    ubicacionPdM    = new Ubicacion("Av. Rivadavia", 5000);
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
  public void unaLineaPuedeSaberSiNoTieneUnaParadaSegunUbicacion() {
    Ubicacion ubicacionError = new Ubicacion("Av. Carabobo", 3700);

    assertThrows(ExcepcionParadasTransporteNoIncluidasEnLinea.class, () -> lineaA.findParada(ubicacionError));
  }

  @Test
  public void unaLineaPuedeSaberSiTieneUnaParadaSegunUbicacion() {
    Ubicacion ubicacionValida = new Ubicacion("Av. Rivadavia", 5000);

    assertDoesNotThrow(() -> lineaA.findParada(ubicacionValida));
  }

  @Test
  public void noPuedoCrearUnTramoEntreParadasQNoFormenParteDelTransportePublicoEnCuestion() {
    Ubicacion ubicacionError = new Ubicacion("Av. Carabobo", 3700);
    Ubicacion ubicacionError2 = new Ubicacion("Av. Rivadavia", 3700);
    Parada paradaError1 = new Parada("Av. Carabobo, 3700", ubicacionError,
        new Distancia(700, UnidadDistancia.MTS));
    Parada paradaError2 = new Parada("Av. Rivadavia, 3700", ubicacionError2,
        new Distancia(700, UnidadDistancia.MTS));

    TransportePublico transportePublico = new TransportePublico(TipoTransportePublico.SUBTE,
        lineaA);

    assertThrows(ExcepcionParadasTransporteNoIncluidasEnLinea.class, () ->
        new Tramo(transportePublico, paradaError1, paradaError2));
  }

  @Test
  public void noPuedoCrearUnTramoEntreParadasQUnaNoYUnaSiFormenParteDelTransportePublicoEnCuestion() {
    Ubicacion ubicacionValida = new Ubicacion("Av. Carabobo", 3700);
    Ubicacion ubicacionError2 = new Ubicacion("Av. Rivadavia", 5000);
    Parada paradaValida1 = new Parada("Av. Carabobo, 3700", ubicacionValida,
        new Distancia(700, UnidadDistancia.MTS));
    Parada paradaError2 = new Parada("Av. Rivadavia, 3700", ubicacionError2,
        new Distancia(700, UnidadDistancia.MTS));

    TransportePublico transportePublico = new TransportePublico(TipoTransportePublico.SUBTE,
        lineaA);

    assertThrows(ExcepcionParadasTransporteNoIncluidasEnLinea.class, () ->
        new Tramo(transportePublico, paradaValida1, paradaError2));
  }

  @Test
  public void puedoCrearUnTramoEntreParadasQNoFormenParteDelTransportePublicoEnCuestion() {
    Ubicacion ubicacionValida = new Ubicacion("Av. Rivadavia", 200);
    Ubicacion ubicacionValida2 = new Ubicacion("Av. Rivadavia", 5000);
    Parada paradaValida1 = new Parada("Av. Carabobo, 3700", ubicacionValida,
        new Distancia(700, UnidadDistancia.MTS));
    Parada paradaValida2 = new Parada("Av. Rivadavia, 3700", ubicacionValida2,
        new Distancia(700, UnidadDistancia.MTS));

    TransportePublico transportePublico = new TransportePublico(TipoTransportePublico.SUBTE,
        lineaA);

    assertDoesNotThrow(() -> new Tramo(transportePublico, paradaValida1, paradaValida2));
  }

}
