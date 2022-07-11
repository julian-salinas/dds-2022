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

  public List<Contacto> getContactos() {
    throw new UnsupportedOperationException("Funcionalidad a implementar");
  }

  public double calculoHC(){
    double hcDatosActividad = datosActividades.stream().mapToDouble(datoActividad -> datoActividad.impactoHC()).sum();
    double hcTransporteMiembros = sectores.stream().mapToDouble(sector -> sector.calculoHC()).sum();
    //DatosActividades HCtransporteMiembros = new DatosActividades("Distancia media", distanciaRecorridadMiembros, );

    return hcDatosActividad + hcTransporteMiembros;
  }
}
