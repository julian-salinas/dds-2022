package presentacion;

import domain.repositorios.RepositorioTransportes;
import domain.repositorios.RepositorioUsuarios;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.trayecto.transporte.nopublico.*;
import domain.trayecto.transporte.publico.Linea;
import domain.trayecto.transporte.publico.Parada;
import domain.trayecto.transporte.publico.TipoTransportePublico;
import domain.trayecto.transporte.publico.TransportePublico;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.distancia.UnidadDistancia;

import java.util.List;

public class Bootstrap {
  public static void init() {
    // Cosas que queramos que pasen al iniciar el server.
    Usuario mati = new Usuario("Matias", "1234", TipoUsuario.MIEMBRO);
    Usuario gonza = new Usuario("Gonzalo", "abcd", TipoUsuario.MIEMBRO);

    if(RepositorioUsuarios.getInstance().findByUsername("Matias") == null) {
      RepositorioUsuarios.getInstance().add(mati);
      RepositorioUsuarios.getInstance().add(gonza);
    }

    Pie pie = new Pie();
    Bicicleta bicicleta = new Bicicleta();
    ServicioContratado servicioContratado = new ServicioContratado(new TipoServicioContratado("taxi"), 200.0);
    VehiculoParticular vehiculoParticular = new VehiculoParticular(TipoDeVehiculo.AUTO, TipoDeCombustible.GASOIL, 430.0);
    Ubicacion ubicacion = new Ubicacion("Rivadavia", 500, "ARGENTINA", "BUENOS AIRES",
        "AVELLANEDA", "AVELLANEDA");
    Parada parada = new Parada("San Pedrito", ubicacion, new Distancia(660.0, UnidadDistancia.MTS));
    Linea linea = new Linea("Linea A");
    linea.agregarParada(parada);
    TransportePublico transportePublico = new TransportePublico(TipoTransportePublico.SUBTE, linea);

    MedioDeTransporte medio = RepositorioTransportes.getInstance().get(1);
    List<MedioDeTransporte> medios = RepositorioTransportes.getInstance().all();
    if(medios.isEmpty()) {
      RepositorioTransportes.getInstance().add(pie);
      RepositorioTransportes.getInstance().add(bicicleta);
      RepositorioTransportes.getInstance().add(servicioContratado);
      RepositorioTransportes.getInstance().add(vehiculoParticular);
      RepositorioTransportes.getInstance().add(transportePublico);
    } else {
      System.out.println("xd");
    }

  }
}
