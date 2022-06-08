package domain.miembros;

import domain.excepciones.ExcepcionNoExisteElSectorEnLaOrganizacion;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.trayecto.Trayecto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Miembro {
  private final String nombre;
  private final String apellido;
  private String tipo; // no se especifica nada
  private String nroDeDocumento; // podria ser int
  @Setter @Getter private Sector sectorDondeTrabaja;
  @Getter private final List<Trayecto> trayectos = new ArrayList<>();

  public Miembro(String nombre, String apellido, String tipo, String nroDeDocumento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.tipo = tipo;
    this.nroDeDocumento = nroDeDocumento;
  }

  public void registrarTrayecto(Trayecto trayecto) {
    // Puede que tenga que ser con muchos trayectos en vez de uno
    trayectos.add(trayecto);
  }

  public void vincularTrabajadorConOrg(Organizacion org, Sector sector) {
    if (org.containsSector(sector)) {
      sector.agregarMiembroParaAceptar(this);
    } else {
      throw new ExcepcionNoExisteElSectorEnLaOrganizacion();
    }
  }

}
