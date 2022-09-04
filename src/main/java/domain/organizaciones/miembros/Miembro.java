package domain.organizaciones.miembros;

import domain.organizaciones.excepciones.ExcepcionNoExisteElSectorEnLaOrganizacion;
import domain.organizaciones.Organizacion;
import domain.organizaciones.sectores.Sector;
import domain.organizaciones.hc.UnidadHC;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.hc.HC;
import domain.trayecto.Trayecto;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Miembro {
  private final String nombre;
  private final String apellido;
  private TipoDeDocumento tipoDeDocumento; // no se especifica nada
  private Integer nroDeDocumento;
  @Setter @Getter private Sector sectorDondeTrabaja;
  @Getter private final List<Trayecto> trayectos = new ArrayList<>();

  public Miembro(String nombre, String apellido, TipoDeDocumento tipoDeDocumento, Integer nroDeDocumento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.tipoDeDocumento = tipoDeDocumento;
    this.nroDeDocumento = nroDeDocumento;
  }

  public void agregarTrayecto(Trayecto trayecto) {
    // para probar unas cosas
    trayectos.add(trayecto);
  }

  public void registrarTrayecto(Trayecto trayecto) {
    // Puede que tenga que ser con muchos trayectos en vez de uno
    trayecto.setOwner(this);
    trayectos.add(trayecto);
  }

  public boolean containsTrayecto(Trayecto trayecto) {
    return trayectos.contains(trayecto);
  }

  public void vincularTrabajadorConOrg(Organizacion org, Sector sector) {
    if (org.containsSector(sector)) {
      sector.agregarMiembroParaAceptar(this);
    } else {
      throw new ExcepcionNoExisteElSectorEnLaOrganizacion();
    }
  }

  public HC calculoHCPersonal(FactorEmision fe){
    return new HC(fe.getValor() * trayectos.stream().mapToDouble(Trayecto::combustibleTotalUtilizado).sum(), UnidadHC.kgCO2);
  }

}
