package domain.organizaciones;

public enum ClasificacionOrg {
  MINISTERIO, UNIVERSIDAD, ESCUELA, EMPRESA_SECTOR_PRIMARIO, EMPRESA_SECTOR_SECUNDARIO;

  @Override
  public String toString() {
    if(this.equals(MINISTERIO))
      return "Ministerio";
    else if(this.equals(UNIVERSIDAD))
      return "Universidad";
    else if(this.equals(ESCUELA))
      return "Escuela";
    else if(this.equals(EMPRESA_SECTOR_PRIMARIO))
      return "Empresa del Sector Primario";
    else if(this.equals(EMPRESA_SECTOR_SECUNDARIO))
      return "Empresa del Sector Secundario";
    else
      return null;
  }

}
