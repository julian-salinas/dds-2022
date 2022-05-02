package com;

import java.util.ArrayList;
import java.util.List;

public class Miembro {
  String nombre;
  String apellido;
  String tipo; // no se especifica nada
  String nroDeDocumento; // podria ser int
  List<TrabajoMiembro> trabajos = new ArrayList<>();
  List<Trayecto> trayectos = new ArrayList<>();

  public Miembro(String nombre, String apellido, String tipo, String nroDeDocumento){
    this.nombre = nombre;
    this.apellido = apellido;
    this.tipo = tipo;
    this.nroDeDocumento = nroDeDocumento;
  }

  public void agregarTrabajo(TrabajoMiembro trabajo){
    trabajos.add(trabajo);
  }

  public void vincularTrabajadorConOrg(Organizacion org, Sector sector){

  }

}
