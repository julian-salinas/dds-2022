package domain.organizaciones.datos.actividades.tipos;

public class NoCoincidenUnidadesFEYTC extends RuntimeException {
  public NoCoincidenUnidadesFEYTC() {
    super("No se pudo cargar el FE al TC, dado que las unidades no coinciden");
  }
}