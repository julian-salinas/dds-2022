package dominio.organizacionymiembros;

import dominio.trayecto.Trayecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Miembro {
  String nombre;
  String apellido;
  String tipo; // no se especifica nada
  String nroDeDocumento; // podria ser int
  List<TrabajoMiembro> posiblesTrabajos = new ArrayList<>();
  List<TrabajoMiembro> trabajos = new ArrayList<>();
  List<Trayecto> trayectos = new ArrayList<>();

  public Miembro(String nombre, String apellido, String tipo, String nroDeDocumento){
    this.nombre = nombre;
    this.apellido = apellido;
    this.tipo = tipo;
    this.nroDeDocumento = nroDeDocumento;
  }

  public List<TrabajoMiembro> getTrabajos(){
    return trabajos;
  }

  public void agregarTrabajo(TrabajoMiembro trabajo){
    this.trabajos.add(trabajo);
  }

  /* Puede que lo quieran asi. No se si el addAll funciona con esto.
  public void registrarTrayectos(List<Trayecto> trayectos){
    this.trayectos.addAll(trayectos);
  }
  */
  public void registrarTrayecto(Trayecto trayecto){
    this.trayectos.add(trayecto);
  }

  public void vincularTrabajadorConOrg(Organizacion org, Sector sector){
    this.posiblesTrabajos.add(new TrabajoMiembro(org, sector));
    org.requestAgregarMiembro(this, sector);
  }

  public void aceptadoPorOrganizacion(Organizacion org){
    /*
    TrabajoMiembro trabajo = this.posiblesTrabajos.stream()
        .filter(job -> job.organizacion == org).findAny().get();*/

    // Creeria que es asi, puede que lo tengamos que revisar

    Optional<TrabajoMiembro> trabajo = this.posiblesTrabajos.stream()
        .filter(job -> job.organizacion == org).findAny();
    if(trabajo.isPresent()){
      this.posiblesTrabajos.remove(trabajo.get());
      this.agregarTrabajo(trabajo.get());
      trabajo.get().sector.agregarMiembro(this);
    }
    else
    {
      // throw error
    }
  }

}
