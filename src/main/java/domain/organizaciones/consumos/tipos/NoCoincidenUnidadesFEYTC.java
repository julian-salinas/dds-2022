package domain.organizaciones.consumos.tipos;

public class NoCoincidenUnidadesFEYTC extends RuntimeException {
  public NoCoincidenUnidadesFEYTC() {
    super("No se pudo cargar la FE al TC, dado que las unidades no coinciden");
  }
}