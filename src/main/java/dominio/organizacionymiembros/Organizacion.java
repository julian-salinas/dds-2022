package dominio.organizacionymiembros;

import dominio.organizacionymiembros.excepcionesOrgMiembros.ClasificacionOrganizacion;
import dominio.organizacionymiembros.excepcionesOrgMiembros.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import dominio.organizacionymiembros.excepcionesOrgMiembros.ExcepcionNoExisteElSectorEnLaOrganizacion;

import java.util.ArrayList;
import java.util.List;

public class Organizacion {
  String razonSocial;
  TipoOrganizacion tipo;
  String ubicacion;
  List<Miembro> miembrosParaAceptar = new ArrayList<>();
  List<Sector> sectores = new ArrayList<>();
  ClasificacionOrganizacion clasificacion; // podria ser una class para evitar errores

  public Organizacion(String razonSocial, TipoOrganizacion tipo, String ubicacion, ClasificacionOrganizacion clasificacion){
    this.razonSocial = razonSocial;
    this.tipo = tipo;
    this.ubicacion = ubicacion;
    this.clasificacion = clasificacion;
    // Se puede agregar al constructor la lista de sectores
  }

  public void agregarSector(Sector sector){
    this.sectores.add(sector);
  }

  public List<Sector> getSectores(){
    return sectores;
  }

  public void requestAgregarMiembro(Miembro miembro, Sector sector){
    if (this.sectores.contains(sector)) {
      this.miembrosParaAceptar.add(miembro);
    }
    else{
      // throw exception "El sector al que se quiere unir no existe"
      throw new ExcepcionNoExisteElSectorEnLaOrganizacion();
    }
  }

  public void aceptarVinculacionDeTrabajador(Miembro miembro){
    if(this.miembrosParaAceptar.contains(miembro)) {
      this.miembrosParaAceptar.remove(miembro);
      miembro.aceptadoPorOrganizacion(this);
    }
    else{
      // throw exception "Se trato de aceptar a un miembro que no pidio vincularse o no existe"
      throw new ExcepcionNoExisteElMiembroAacptarEnLaOrg();
    }
  }

}
