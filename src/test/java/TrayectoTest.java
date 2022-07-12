import domain.miembros.Miembro;
import domain.miembros.TipoDeDocumento;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.TrayectoCompartido;
import domain.trayecto.transporte.*;
import domain.trayecto.transporte.excepciones.ExcepcionParadasTransporteNoIncluidasEnLinea;
import domain.trayecto.transporte.excepciones.ExcepcionTipoTransporteNoIgualAtipoDeLinea;
import domain.ubicaciones.Distancia;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.UnidadDeDistancia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrayectoTest {

  Linea lineaDefault;
  Parada paradaDefault1, paradaDefault2;
  //Trayecto trayectoDefault;
  List<Parada> paradasLineaA;
  Ubicacion ubicacionDefault;

  @BeforeEach
  void init() {
    /*try {
      ubicacionDefault = new Ubicacion("Corrientes", 1200, "PUERTO LEONI");
    } catch (IOException e) {
      //xd
    }*/
    ubicacionDefault = mock(Ubicacion.class);

    paradasLineaA = new ArrayList<>();

    lineaDefault = new Linea("A", paradasLineaA, TipoTransportePublico.SUBTE);

    paradaDefault1 = new Parada("Carabobo", new Distancia(4, UnidadDeDistancia.KM));
    paradaDefault2 = new Parada("Puan", new Distancia(5, UnidadDeDistancia.KM));

    //trayectoDefault = new Trayecto();
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
    Parada paradaError1 = new Parada("Acoyte", new Distancia(700, UnidadDeDistancia.MTS));
    Parada paradaError2 = new Parada("Castro Barros", new Distancia(6, UnidadDeDistancia.MTS));
    // Nunca los agrego a lineaDefault
    assertThrows(ExcepcionParadasTransporteNoIncluidasEnLinea.class, () -> new TransportePublico(TipoTransportePublico.SUBTE, lineaDefault, paradaError1, paradaError2));
  }

  @Test
  public void trayectosCompartidosOK(){
    Miembro miembro = new Miembro("", "", TipoDeDocumento.DNI, 0);
    Miembro miembro2 = new Miembro("", "", TipoDeDocumento.DNI, 0);
    Miembro miembro3 = new Miembro("", "", TipoDeDocumento.DNI, 0);
    List<Miembro> miembros = new ArrayList<>();
    miembros.add(miembro2);
    miembros.add(miembro3);
    Tramo tramo = new Tramo(new ServicioContratado(new TipoServicioContratado("Uber"), ubicacionDefault, ubicacionDefault));
    Tramo tramo2 = new Tramo(new ServicioContratado(new TipoServicioContratado("Uber"), ubicacionDefault, ubicacionDefault));
    List<Tramo> tramos = new ArrayList<>();
    tramos.add(tramo);
    tramos.add(tramo2);
    TrayectoCompartido tcomp = new TrayectoCompartido(miembros, tramos);
    miembro.registrarTrayecto(tcomp);
    assertTrue(miembro.getTrayectos().contains(tcomp));
    assertTrue(tcomp.ownerIs(miembro));
  }

}
