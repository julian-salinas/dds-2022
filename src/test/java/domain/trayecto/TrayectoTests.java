package domain.trayecto;

import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.trayecto.transporte.nopublico.Bicicleta;
import domain.trayecto.transporte.nopublico.ServicioContratado;
import domain.trayecto.transporte.nopublico.TipoServicioContratado;
import domain.ubicaciones.Ubicacion;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrayectoTests {

  Ubicacion ubicacionDefault;

  @BeforeEach
  void init() {
    ubicacionDefault = new Ubicacion();
  }

  // Trayecto Compartido -> Solo Vehiculos Particulares (VP) o Servicios Contratados (SC)

  @Test
  public void noPuedoCrearUnTrayectoCompartidoQueNoSeaDeTipoVPoSC() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);

    Bicicleta bici = new Bicicleta();
    Tramo tramo = new Tramo(bici, ubicacionDefault, ubicacionDefault);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido();
    trayectoCompartido.agregarAcompanante(miembro2);
    miembro.registrarTrayecto(trayectoCompartido);

    assertThrows(RuntimeException.class, () -> trayectoCompartido.agregarTramos(tramos));
  }

  @Test
  public void puedoCrearUnTrayectoCompartidoEntreDosPibes() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(taxi, 500.0);
    Tramo tramo = new Tramo(servicioContratado, ubicacionDefault, ubicacionDefault);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido();
    trayectoCompartido.agregarTramos(tramos);

    assertDoesNotThrow(() -> trayectoCompartido.agregarAcompanantes(miembro2));
    trayectoCompartido.agregarAcompanante(miembro2);
    assertDoesNotThrow(() -> miembro.registrarTrayecto(trayectoCompartido));

  }

  @Test
  public void elOwnerDeUnTrayectoCompartidoEntreDosPibesEsElCorrecto() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, 500.0
    );
    Tramo tramo = new Tramo(servicioContratado, ubicacionDefault, ubicacionDefault);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido();
    trayectoCompartido.agregarTramos(tramos);
    trayectoCompartido.agregarAcompanante(miembro2);

    miembro.registrarTrayecto(trayectoCompartido); // owner -> miembro

    assertTrue(trayectoCompartido.ownerIs(miembro));
    assertFalse(trayectoCompartido.ownerIs(miembro2));

  }

  @Test
  public void UnTrayectoCompartidoEntreDosPibesFiguraEnAmbosPibes() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, 500.0
    );
    Tramo tramo = new Tramo(servicioContratado, ubicacionDefault, ubicacionDefault);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido();
    trayectoCompartido.agregarTramos(tramos);
    trayectoCompartido.agregarAcompanante(miembro2);

    miembro.registrarTrayecto(trayectoCompartido); // owner -> miembro

    assertTrue(miembro.containsTrayecto(trayectoCompartido));
    assertTrue(miembro2.containsTrayecto(trayectoCompartido));

  }


  // De aca para abajo se prueban funcionalidades q puede q no se usen

  @Test
  public void aUnTrayectoCompartidoSeLePuedenPedirLosMiembros() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);
    List<Miembro> miembros = Stream.of(miembro, miembro2).collect(Collectors.toList());

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, 500.0
    );
    Tramo tramo = new Tramo(servicioContratado, ubicacionDefault, ubicacionDefault);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido();
    trayectoCompartido.agregarTramos(tramos);
    trayectoCompartido.agregarAcompanante(miembro2);

    miembro.registrarTrayecto(trayectoCompartido); // owner -> miembro

    assertEquals(miembros, trayectoCompartido.getMiembros());

  }

}
