package dominio.organizacionymiembros;

import java.util.ArrayList;
import java.util.List;

public class Organizacion {
  String razonSocial;
  TipoOrganizacion tipo;
  String ubicacion;
  List<Miembro> miembrosParaAceptar = new ArrayList<>();
  List<Sector> sectores = new ArrayList<>();
  String clasificacion; // podria ser una class para evitar errores

  public Organizacion(String razonSocial, TipoOrganizacion tipo, String ubicacion, String clasificacion){
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

  public void requestAgregarMiembro(Miembro miembro){
    this.miembrosParaAceptar.add(miembro);
  }

  public void aceptarVinculacionDeTrabajador(Miembro miembro){
    if(this.miembrosParaAceptar.contains(miembro)) {
      this.miembrosParaAceptar.remove(miembro);
      miembro.aceptadoPorOrganizacion(this);
    }
    else{
      // throw error
    }
  }

}
