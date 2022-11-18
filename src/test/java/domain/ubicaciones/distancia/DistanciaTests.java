package domain.ubicaciones.distancia;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.servicios.geodds.ServicioGeoDds;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.TrayectoCompartido;
import domain.trayecto.transporte.nopublico.*;
import domain.trayecto.transporte.publico.Linea;
import domain.trayecto.transporte.publico.Parada;
import domain.trayecto.transporte.publico.TipoTransportePublico;
import domain.trayecto.transporte.publico.TransportePublico;
import domain.ubicaciones.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DistanciaTests {

  ServicioGeoDds apiClient;

  Linea lineaA;

  Tramo tramoLineaA;
  Tramo tramoLineaAinverso;
  Tramo tramoAPie;
  Tramo tramoEnBici;
  Tramo tramoEnAuto;
  Tramo tramoEnTaxi;

  // VP -> Vehiculo Particular
  // SC -> Servicio Contratado

  Ubicacion ubicacionInicioPie;
  Ubicacion ubicacionInicioBici;
  Ubicacion ubicacionInicioVP;
  Ubicacion ubicacionInicioSC;
  Ubicacion ubicacionFinPie;
  Ubicacion ubicacionFinBici;
  Ubicacion ubicacionFinVP;
  Ubicacion ubicacionFinSC;

  @BeforeEach
  public void init() throws IOException {
    apiClient = mock(ServicioGeoDds.class);

    Ubicacion ubicacionSanPedrito = new Ubicacion("Rivadavia", 1800);
    Ubicacion ubicacionFlores     = new Ubicacion("Rivadavia", 1900);
    Ubicacion ubicacionCarabobo   = new Ubicacion("Rivadavia", 2000);
    Ubicacion ubicacionPuan       = new Ubicacion("Rivadavia", 2100);

    Parada sanPedrito = new Parada("San Pedrito", ubicacionSanPedrito, distanciaMts(300.0));
    Parada flores     = new Parada("San Jose de Flores", ubicacionFlores, distanciaMts(200.0));
    Parada carabobo   = new Parada("Carabobo", ubicacionCarabobo, distanciaMts(150.0));
    Parada puan       = new Parada("Puan", ubicacionPuan, distanciaMts(150.0));
    List<Parada> paradasLineaA = Stream.of(sanPedrito, flores, carabobo, puan)
        .collect(Collectors.toList());

    lineaA = new Linea("Linea A", paradasLineaA);
    lineaA.setUnidireccional();

    // Creo un recorrido que es solo de San Pedrito a Carabobo
    TransportePublico recorridoConLineaA = new TransportePublico(TipoTransportePublico.SUBTE,
        lineaA);
    tramoLineaA = new Tramo(recorridoConLineaA, sanPedrito, carabobo);

    // Con un recorrido que es solo de Carabobo a San Pedrito
    TransportePublico recorridoConLineaAinverso = new TransportePublico(TipoTransportePublico.SUBTE,
        lineaA);
    tramoLineaAinverso = new Tramo(recorridoConLineaAinverso, carabobo, sanPedrito);

    ubicacionInicioPie = crearUbicacion("Directorio", 1700);
    ubicacionInicioBici = crearUbicacion("Directorio", 100);
    ubicacionInicioVP = crearUbicacion("Directorio", 2300);
    ubicacionInicioSC = crearUbicacion("Directorio", 600);
    ubicacionFinPie = crearUbicacion("Yapeyu", 600);
    ubicacionFinBici = crearUbicacion("Yapeyu", 200);
    ubicacionFinVP = crearUbicacion("Yapeyu", 2400);
    ubicacionFinSC = crearUbicacion("Yapeyu", 700);

    Pie recorridoAPie = new Pie();
    tramoAPie   = new Tramo(recorridoAPie, ubicacionInicioPie, ubicacionFinPie);

    Bicicleta recorridoEnBici = new Bicicleta();
    tramoEnBici = new Tramo(recorridoEnBici, ubicacionInicioBici, ubicacionFinBici);

    VehiculoParticular recorridoEnAuto = new VehiculoParticular(TipoDeVehiculo.AUTO,
        TipoDeCombustible.GASOIL, 400.0);
    tramoEnAuto = new Tramo(recorridoEnAuto, ubicacionInicioVP, ubicacionFinVP);

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado recorridoEnTaxi = new ServicioContratado(taxi, 200.0);
    tramoEnTaxi = new Tramo(recorridoEnTaxi, ubicacionInicioSC,
        ubicacionFinSC);

  }

  // Tests con Transporte Publico (la dist. de cada tramo se indica a mano)

  // Unidireccional:
  //  - Las paradas estan seteadas de forma circular (despues de la ultima esta la primera)
  //  - Se circula solo en un sentido (para adelante)
  //  - Lo importante en este caso es ver si se tiene que pasar de la ultima a la primera (cruzar)

  // Bidireccional:
  //  - Se circula en ambos sentidos
  //  - Lo importante en este caso es ver si se tiene que ir para atras (el sentido)

  @Test
  public void laDistanciaDeUnTramoDeTransportePublicoIdaDistintaDeVueltaSeCalculaBien() {
    //San Pedrito -> Carabobo, Unidireccional
    assertEquals(500.0, tramoLineaA.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTramoInversoDeTransportePublicoIdaDistintaDeVueltaSeCalculaBien() {
    //Carabobo -> San Pedrito, Unidireccional
    assertEquals(300.0, tramoLineaAinverso.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTramoDeTransportePublicoIdaIgualAVueltaSeCalculaBien() {
    //San Pedrito -> Carabobo, Bidireccional
    lineaA.setBidireccional();
    assertEquals(500.0, tramoLineaA.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTramoInversoDeTransportePublicoIdaIgualAVueltaSeCalculaBien() {
    //Carabobo -> San Pedrito, Bidireccional
    lineaA.setBidireccional();
    assertEquals(500.0, tramoLineaAinverso.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTrayectoDeTramosDeTransportePublicoSeCalculaBien() {
    Ubicacion ubicacionCarabobo1200 = new Ubicacion("Carabobo", 1200);
    Ubicacion ubicacionCarabobo1400 = new Ubicacion("Carabobo", 1400);
    Parada carabobo1200 = new Parada("Carabobo1200", ubicacionCarabobo1200, distanciaMts(55.0));
    Parada carabobo1400 = new Parada("Carabobo1400", ubicacionCarabobo1400, distanciaMts(55.0));
    List<Parada> paradas132 = Stream.of(carabobo1200, carabobo1400).collect(Collectors.toList());
    Linea linea132 = new Linea("132", paradas132);
    TransportePublico recorridoCon132 = new TransportePublico(TipoTransportePublico.COLECTIVO, linea132);
    Tramo tramoCon132 = new Tramo(recorridoCon132, carabobo1200, carabobo1400);

    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramoLineaA, tramoCon132);

    assertEquals(555.0, trayecto.distanciaTotal().valorEnMetros());
  }

  // Tests con los demas tipos de Transporte (la dist. de cada tramo se la indica la API)

  @Test
  public void laDistanciaDeUnTramoAPieSeCalculaBien() throws IOException {

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioPie, ubicacionFinPie))
        .thenReturn(73.6);

    assertEquals(73.6, tramoAPie.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTramoEnBiciSeCalculaBien() throws IOException {

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioBici, ubicacionFinBici))
        .thenReturn(313.47);

    assertEquals(313.47, tramoEnBici.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTramoEnVehiculoParticularSeCalculaBien() throws IOException {

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioVP, ubicacionFinVP))
        .thenReturn(10002.2);

    assertEquals(10002.2, tramoEnAuto.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTramoEnServicioContratadoSeCalculaBien() throws IOException {

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioSC, ubicacionFinSC))
        .thenReturn(750.0);

    assertEquals(750.0, tramoEnTaxi.distancia().valorEnMetros());
  }

  @Test                                               //TP = Transporte Publico
  public void laDistanciaDeUnTrayectoDeTramosDeTodoMenosTPSeCalculaBien() throws IOException {

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioPie, ubicacionFinPie)).thenReturn(73.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioBici, ubicacionFinBici)).thenReturn(313.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioVP, ubicacionFinVP)).thenReturn(10000.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioSC, ubicacionFinSC)).thenReturn(750.0);

    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramoAPie, tramoEnBici, tramoEnAuto, tramoEnTaxi);

    // 73.0 + 313.0 + 10000.0 + 750.0 = 11136.0
    assertEquals(11136.0, trayecto.distanciaTotal().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTrayectoDeTramosDeTodosLosTiposSeCalculaBien() throws IOException {

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioPie, ubicacionFinPie)).thenReturn(73.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioBici, ubicacionFinBici)).thenReturn(313.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioVP, ubicacionFinVP)).thenReturn(10000.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioSC, ubicacionFinSC)).thenReturn(750.0);

    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramoAPie, tramoEnBici, tramoEnAuto, tramoEnTaxi, tramoLineaA);

    // 73.0 + 313.0 + 10000.0 + 750.0 + 500.0 = 11786.0
    assertEquals(11636.0, trayecto.distanciaTotal().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTrayectoDeTramosDeTodosLosTiposMasDeUnaVezSeCalculaBien()
      throws IOException {

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioPie, ubicacionFinPie)).thenReturn(73.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioBici, ubicacionFinBici)).thenReturn(313.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioVP, ubicacionFinVP)).thenReturn(10000.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioSC, ubicacionFinSC)).thenReturn(750.0);

    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramoAPie, tramoEnBici, tramoEnAuto,
        tramoEnTaxi, tramoLineaA, tramoAPie, tramoEnAuto);

    // 73.0 + 313.0 + 10000.0 + 750.0 + 500.0 + 73.0 + 10000.0 = 21859.0
    assertEquals(21709.0, trayecto.distanciaTotal().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTrayectoCompartidoDeTramosDeTodosLosTiposMasDeUnaVezSeCalculaBien()
      throws IOException {

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioVP, ubicacionFinVP)).thenReturn(10000.0);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicioSC, ubicacionFinSC)).thenReturn(750.0);

    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);
    List<Miembro> miembros = Stream.of(miembro, miembro2).collect(Collectors.toList());

    TrayectoCompartido trayecto = new TrayectoCompartido();
    // miembros, new ArrayList<>()
    trayecto.agregarAcompanantes(miembros);
    trayecto.agregarTramos(tramoEnAuto,
        tramoEnTaxi, tramoEnAuto);

    // 10000.0 + 750.0 + 10000.0 = 20750.0
    assertEquals(20750.0, trayecto.distanciaTotal().valorEnMetros());
  }



  // Metodos aux.

  private Distancia distanciaMts(double valor) {
    return new Distancia(valor, UnidadDistancia.MTS);
  }

  private Distancia distanciaKm(double valor) {
    return new Distancia(valor, UnidadDistancia.KM);
  }

  private Ubicacion crearUbicacion(String calle, int altura) {
    return new Ubicacion(calle, altura, apiClient);
  }

}
