package domain.organizaciones.consumos.tipos;

import domain.organizaciones.consumos.Actividad;
import domain.organizaciones.consumos.Alcance;
import domain.organizaciones.consumos.Unidad;

public class TipoDeConsumoFactory {

  private static final TipoDeConsumoFactory INSTANCE = new TipoDeConsumoFactory();

  public static TipoDeConsumoFactory instance() {
    return INSTANCE;
  }

  public TipoDeConsumo buildTipoDeConsumo(String tipo) {

    switch (tipo){

      default:
      case "Gas Natural":
        return new TipoDeConsumo(Unidad.M3,
            Actividad.COMBUSTION_FIJA,
            Alcance.DIRECTAS);

      case "Diesel/Gasoil":
      case "Nafta":
        return new TipoDeConsumo(Unidad.LT,
            Actividad.COMBUSTION_FIJA,
            Alcance.DIRECTAS);

      case "Carbon":
        return new TipoDeConsumo(Unidad.KG,
            Actividad.COMBUSTION_FIJA,
            Alcance.DIRECTAS);

      case "Combustible Gasoil":
      case "Combustible Nafta":
        return new TipoDeConsumo(Unidad.LT,
            Actividad.COMBUSTION_MOVIL,
            Alcance.DIRECTAS);

      case "Electricidad":
        return new TipoDeConsumo(Unidad.KWH,
            Actividad.ELECTRICIDAD,
            Alcance.INDIRECTAS_ELECTRICIDAD);

      case "Camion de carga":
      case "Utilitario liviano":
        return new TipoDeConsumo(Unidad.NINGUNA,
            Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
            Alcance.INDIRECTAS_EXTERNAS);

      case "Distancia media":
        return new TipoDeConsumo(Unidad.KM,
            Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
            Alcance.INDIRECTAS_EXTERNAS);
    }
  }

}
