package domain.organizaciones.miembros;

public enum TipoDeDocumento {
  DNI, // Documento nacional de identidad
  LC, // Libreta cívica
  LE, // Libreta de enrolamiento
  CI // Cédula de identidad
  ;

  @Override
  public String toString() {
    if(this.equals(DNI))
      return "DNI";
    else if (this.equals(LC))
      return "Libreta Civica";
    else if (this.equals(LE))
      return "Libreta de enrolamiento";
    else if (this.equals(CI))
      return "Cedula de Identidad";
    else
      throw new RuntimeException("Exploto con el enum TipoDeDocumento");
  }
}
