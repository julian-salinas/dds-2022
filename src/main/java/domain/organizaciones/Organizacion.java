package domain.organizaciones;

import domain.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.miembros.Miembro;
import domain.ubicaciones.Ubicacion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Organizacion {
  private final String razonSocial;
  private TipoOrganizacion tipo;
  private Ubicacion ubicacion;
  private final List<Sector> sectores = new ArrayList<>();
  private ClasificacionOrganizacion clasificacion;
  private List<DatosActividades> datosActividades = new ArrayList<>();

  public Organizacion(String razonSocial, TipoOrganizacion tipo,
                      Ubicacion ubicacion, ClasificacionOrganizacion clasificacion) {
    this.razonSocial = razonSocial;
    this.tipo = tipo;
    this.ubicacion = ubicacion;
    this.clasificacion = clasificacion;
  }

  public boolean containsSector(Sector sector) {
    return sectores.contains(sector);
  }

  public void agregarSector(Sector sector) {
    sector.setOrgAlaQuePertenezco(this);
    sectores.add(sector);
  }

  public void aceptarVinculacionDeTrabajador(Miembro miembro, Sector sector) {
    if (sector.containsMiembroParaAceptar(miembro)) {
      sector.sacarMiembroParaAceptar(miembro);
      sector.agregarMiembro(miembro);
    } else {
      throw new ExcepcionNoExisteElMiembroAacptarEnLaOrg();
    }
  }

  // Funciona si se piensa que cada carga de medicion reemplaza la que estaba anteriormente,
  // no me parece mal, pienso que si quieren cargar mediciones se van a cargar incluyendo tambien las anteriores
  // en el mismo archivo dado que seguramente se tengan en un excel
  public void cargarMediciones(String pathCSV) {
    String linea;

    datosActividades.clear();
    try {
      BufferedReader buffer = new BufferedReader(new FileReader(pathCSV));
      buffer.readLine();
      buffer.readLine(); //Para saltear las dos primeras lineas
      while((linea = buffer.readLine()) != null) {
        String[] fila = linea.split(";");
        datosActividades.add(new DatosActividades(fila[0],fila[1],fila[2],fila[3]));
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public List<DatosActividades> getDatosActividades() {
    return datosActividades;
  }
}
