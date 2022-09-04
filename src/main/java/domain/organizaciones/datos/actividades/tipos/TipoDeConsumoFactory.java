package domain.organizaciones.datos.actividades.tipos;

import domain.organizaciones.datos.actividades.Actividad;
import domain.organizaciones.datos.actividades.Alcance;
import domain.organizaciones.datos.actividades.UnidadConsumo;

public class TipoDeConsumoFactory {

  private static final TipoDeConsumoFactory INSTANCE = new TipoDeConsumoFactory();

  public static TipoDeConsumoFactory instance() {
    return INSTANCE;
  }

  public TipoDeConsumo buildTipoDeConsumo(String tipo) {

    switch (tipo){

      default:
      case "Gas Natural":
        return new TipoDeConsumo(UnidadConsumo.M3,
            Actividad.COMBUSTION_FIJA,
            Alcance.DIRECTAS);

      case "Diesel/Gasoil":
      case "Nafta":
        return new TipoDeConsumo(UnidadConsumo.LT,
            Actividad.COMBUSTION_FIJA,
            Alcance.DIRECTAS);

      case "Carbon":
        return new TipoDeConsumo(UnidadConsumo.KG,
            Actividad.COMBUSTION_FIJA,
            Alcance.DIRECTAS);

      case "Combustible Gasoil":
      case "Combustible Nafta":
        return new TipoDeConsumo(UnidadConsumo.LT,
            Actividad.COMBUSTION_MOVIL,
            Alcance.DIRECTAS);

      case "Electricidad":
        return new TipoDeConsumo(UnidadConsumo.KWH,
            Actividad.ELECTRICIDAD,
            Alcance.INDIRECTAS_ELECTRICIDAD);

      case "Camion de carga":
      case "Utilitario liviano":
        return new TipoDeConsumo(UnidadConsumo.NINGUNA,
            Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
            Alcance.INDIRECTAS_EXTERNAS);

      case "Distancia media":
        return new TipoDeConsumo(UnidadConsumo.KM,
            Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
            Alcance.INDIRECTAS_EXTERNAS);
    }
  }

}
