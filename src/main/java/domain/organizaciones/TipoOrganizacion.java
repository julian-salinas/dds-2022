package domain.organizaciones;

public enum TipoOrganizacion {
  GUBERNAMENTAL, ONG, EMPRESA, INSTITUCION;

  @Override
  public String toString() {
    if(this.equals(GUBERNAMENTAL))
      return "Gubernamental";
    else if(this.equals(ONG))
      return "ONG"; // trvial
    else if(this.equals(EMPRESA))
      return "Empresa";
    else if(this.equals(INSTITUCION))
      return "Institucion";
    else
      return null;
  }

}
