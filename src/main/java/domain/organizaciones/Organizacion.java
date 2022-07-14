package domain.organizaciones;

import domain.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.miembros.Miembro;
import domain.ubicaciones.Localidad;
import domain.ubicaciones.Municipio;
import domain.ubicaciones.Provincia;
import domain.ubicaciones.Ubicacion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Organizacion {
  private final String razonSocial;
  private TipoOrganizacion tipo;
  private Ubicacion ubicacion;
  private final List<Sector> sectores = new ArrayList<>();
  private ClasificacionOrganizacion clasificacion;
  private List<DatosActividades> datosActividades = new ArrayList<>();
  private List<Contacto> contactos = new ArrayList<>();

  public Organizacion(String razonSocial, TipoOrganizacion tipo,
                      Ubicacion ubicacion, ClasificacionOrganizacion clasificacion) {
    this.razonSocial = razonSocial;
    this.tipo = tipo;
    this.ubicacion = ubicacion;
    this.clasificacion = clasificacion;
  }

  // ------------- 1era entrega ---------------------------------

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

  // ------------- 2da entrega ---------------------------------

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

  // ------------- 3era entrega ---------------------------------

  public Ubicacion getUbicacion() {
    return ubicacion;
  }
  public Localidad sectorLocalidad() {
    return ubicacion.getLocalidad();
  }
  public Municipio sectorMunicipio() {
    return ubicacion.getLocalidad().getMunicipio();
  }
  public Provincia sectorProvincia() {
    return ubicacion.getLocalidad().getMunicipio().getProvincia();
  }

  public void agregarContactos(Contacto ... nuevosContactos) {
    Collections.addAll(this.contactos, nuevosContactos);
  }

  public List<Contacto> getContactos() {
    return contactos;
  }

  private void cargarDATransladoMiembros(){
    double combustibleTransporteMiembros = 30 * sectores.stream().mapToDouble(Sector::combustibleConsumidoTransporteMiembros).sum(); // Multiplico por 30, para obtener el valor mensual
    datosActividades.add(new DatosActividades("Distancia media",
        String.valueOf(combustibleTransporteMiembros),
        "Mensual",
        new SimpleDateFormat("MM/yyyy").format(LocalDate.now())));
  }

  private double calculoHCMensual(){
    this.cargarDATransladoMiembros();
    return datosActividades.stream().mapToDouble(DatosActividades::impactoHC).sum();
  }

  public HC HCMensual(){
    double hcDatosActividad = calculoHCMensual();
    return new HC(hcDatosActividad, UnidadHC.kgCO2);
  }

  public HC HCAnual(){
    double hcDatosActividad = calculoHCMensual() * 12;
    return new HC(hcDatosActividad, UnidadHC.kgCO2);
  }
}
