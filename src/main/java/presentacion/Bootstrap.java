package presentacion;

import domain.organizaciones.datos.actividades.Actividad;
import domain.organizaciones.datos.actividades.Alcance;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import domain.repositorios.RepositorioConsumos;
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


    // Cargo Medios de Transporte
    Pie pie = new Pie();
    Bicicleta bicicleta = new Bicicleta();
    ServicioContratado servicioContratado = new ServicioContratado(new TipoServicioContratado("taxi"), 200.0);
    VehiculoParticular vehiculoParticular = new VehiculoParticular(TipoDeVehiculo.AUTO, TipoDeCombustible.GASOIL, 430.0);
    Ubicacion ubicacion = new Ubicacion("Rivadavia", 500, "ARGENTINA", "BUENOS AIRES",
        "AVELLANEDA", "AVELLANEDA");
    Ubicacion ubicacion2 = new Ubicacion("Rivadavia", 700, "ARGENTINA", "BUENOS AIRES",
        "AVELLANEDA", "AVELLANEDA");
    Parada parada  = new Parada("San Pedrito", ubicacion, new Distancia(660.0, UnidadDistancia.MTS));
    Parada parada2 = new Parada("Flores", ubicacion2, new Distancia(660.0, UnidadDistancia.MTS));
    Linea linea = new Linea("Linea A");
    linea.agregarParada(parada);
    linea.agregarParada(parada2);
    TransportePublico transportePublico = new TransportePublico(TipoTransportePublico.SUBTE, linea);

    //MedioDeTransporte medio = RepositorioTransportes.getInstance().get(1);
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

    //TipoDeConsumo cons = RepositorioConsumos.getInstance().findByName("Distancia media");

    // Cargo Tipos De Consumo
    FactorEmision feM3 = new FactorEmision(30.5, UnidadConsumo.M3);
    FactorEmision feLT = new FactorEmision(30.5, UnidadConsumo.LT);
    FactorEmision feKG = new FactorEmision(30.5, UnidadConsumo.KG);
    FactorEmision feKWH = new FactorEmision(30.5, UnidadConsumo.KWH);
    FactorEmision feKM = new FactorEmision(30.5, UnidadConsumo.KM);
    FactorEmision feNinguna = new FactorEmision(30.5, UnidadConsumo.NINGUNA);

    TipoDeConsumo tipo1 = new TipoDeConsumo("Gas Natural",
        UnidadConsumo.M3,
        Actividad.COMBUSTION_FIJA,
        Alcance.DIRECTAS);
    tipo1.cargarFactorEmision(feM3);
    TipoDeConsumo tipo2 = new TipoDeConsumo("Diesel/Gasoil",
        UnidadConsumo.LT,
        Actividad.COMBUSTION_FIJA,
        Alcance.DIRECTAS);
    tipo2.cargarFactorEmision(feLT);
    TipoDeConsumo tipo3 = new TipoDeConsumo("Nafta",
        UnidadConsumo.LT,
        Actividad.COMBUSTION_FIJA,
        Alcance.DIRECTAS);
    tipo3.cargarFactorEmision(feLT);
    TipoDeConsumo tipo4 = new TipoDeConsumo("Carbon",
        UnidadConsumo.KG,
        Actividad.COMBUSTION_FIJA,
        Alcance.DIRECTAS);
    tipo4.cargarFactorEmision(feKG);
    TipoDeConsumo tipo5 = new TipoDeConsumo("Combustible Gasoil",
        UnidadConsumo.LT,
        Actividad.COMBUSTION_MOVIL,
        Alcance.DIRECTAS);
    tipo5.cargarFactorEmision(feLT);
    TipoDeConsumo tipo6 = new TipoDeConsumo("Combustible Nafta",
        UnidadConsumo.LT,
        Actividad.COMBUSTION_MOVIL,
        Alcance.DIRECTAS);
    tipo6.cargarFactorEmision(feLT);
    TipoDeConsumo tipo7 = new TipoDeConsumo("Electricidad",
        UnidadConsumo.KWH,
        Actividad.ELECTRICIDAD,
        Alcance.INDIRECTAS_ELECTRICIDAD);
    tipo7.cargarFactorEmision(feKWH);
    TipoDeConsumo tipo8 = new TipoDeConsumo("Camion de carga",
        UnidadConsumo.NINGUNA,
        Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
        Alcance.INDIRECTAS_EXTERNAS);
    tipo8.cargarFactorEmision(feNinguna);
    TipoDeConsumo tipo9 = new TipoDeConsumo("Utilitario liviano",
        UnidadConsumo.NINGUNA,
        Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
        Alcance.INDIRECTAS_EXTERNAS);
    tipo9.cargarFactorEmision(feNinguna);
    TipoDeConsumo tipo10 = new TipoDeConsumo("Distancia media",
        UnidadConsumo.KM,
        Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
        Alcance.INDIRECTAS_EXTERNAS);
    tipo10.cargarFactorEmision(feKM);

    List<TipoDeConsumo> consumos = RepositorioConsumos.getInstance().all();
    if(consumos.isEmpty()) {
      RepositorioConsumos.getInstance().add(tipo1);
      RepositorioConsumos.getInstance().add(tipo2);
      RepositorioConsumos.getInstance().add(tipo3);
      RepositorioConsumos.getInstance().add(tipo4);
      RepositorioConsumos.getInstance().add(tipo5);
      RepositorioConsumos.getInstance().add(tipo6);
      RepositorioConsumos.getInstance().add(tipo7);
      RepositorioConsumos.getInstance().add(tipo8);
      RepositorioConsumos.getInstance().add(tipo9);
      RepositorioConsumos.getInstance().add(tipo10);
    } else {
      System.out.println("xd");
    }

  }
}
