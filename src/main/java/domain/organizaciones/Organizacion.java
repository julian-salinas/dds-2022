package domain.organizaciones;

import domain.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.miembros.Miembro;
import domain.ubicaciones.Ubicacion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public List<Contacto> getContactos() {
    throw new UnsupportedOperationException("Funcionalidad a implementar");
  }

  public void cargarDATransladoMiembros(){
    double distanciaTransporteMiembros = 30 * sectores.stream().mapToDouble(sector -> sector.distanciaTransporteMiembros()).sum();
    datosActividades.add(new DatosActividades("Translado de Miembros de la Organizacion",
        String.valueOf(distanciaTransporteMiembros),
        "Mensual",
        new SimpleDateFormat("MM/yyyy").format(LocalDate.now())));
  }

  public double calculoHC(){
    this.cargarDATransladoMiembros();
    return datosActividades.stream().mapToDouble(DatosActividades::impactoHC).sum();
  }
}
