package entrega2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.transporte.Linea;
import domain.trayecto.transporte.Parada;
import domain.trayecto.transporte.TipoTransportePublico;
import domain.trayecto.transporte.TransportePublico;
import domain.ubicaciones.Distancia;
import domain.ubicaciones.UnidadDeDistancia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DistanciaTests {

  Parada sanPedrito;
  Parada flores;
  Parada carabobo;
  Parada puan;
  Linea  lineaA;
  TransportePublico recorridoConLineaA;
  Tramo  tramoLineaA;

  @BeforeEach
  public void init() {
    sanPedrito = new Parada("San Pedrito", distanciaMts(300.0));
    flores     = new Parada("San Jose de Flores", distanciaMts(200.0));
    carabobo   = new Parada("Carabobo", distanciaMts(150.0));
    puan       = new Parada("Puan", distanciaMts(150.0));
    List<Parada> paradasLineaA = Stream.of(sanPedrito, flores, carabobo, puan).collect(Collectors.toList());
    lineaA = new Linea("Linea A", paradasLineaA, TipoTransportePublico.SUBTE);
    // Con un recorrido que es solo de San Pedrito a Carabobo
    recorridoConLineaA = new TransportePublico(TipoTransportePublico.SUBTE, lineaA, sanPedrito, carabobo);
    tramoLineaA = new Tramo(recorridoConLineaA);
  }

  // Tests con Transporte Publico (la dist. de cada tramo se indica a mano)

  @Test
  public void laDistanciaDeUnTramoDeTransportePublicoSeCalculaBien() {
    assertEquals(650.0, tramoLineaA.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTrayectoDeTramosDeTransportePublicoSeCalculaBien() {
    Parada carabobo1200 = new Parada("Carabobo1200", distanciaMts(55.0));
    Parada carabobo1400 = new Parada("Carabobo1400", distanciaMts(55.0));
    List<Parada> paradas132 = Stream.of(carabobo1200, carabobo1400).collect(Collectors.toList());
    Linea linea132 = new Linea("132", paradas132, TipoTransportePublico.COLECTIVO);
    TransportePublico recorridoCon132 = new TransportePublico(TipoTransportePublico.COLECTIVO, linea132, carabobo1200, carabobo1400);
    Tramo tramoCon132 = new Tramo(recorridoCon132);

    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramoLineaA, tramoCon132);

    assertEquals(760.0, trayecto.distanciaTotal().valorEnMetros());
  }

  // Tests con los demas tipos de Transporte (la dist. de cada tramo se la indica la API)



  // Metodos aux.

  private Distancia distanciaMts(double valor) {
    return new Distancia(valor, UnidadDeDistancia.MTS);
  }

  private Distancia distanciaKm(double valor) {
    return new Distancia(valor, UnidadDeDistancia.KM);
  }

}
