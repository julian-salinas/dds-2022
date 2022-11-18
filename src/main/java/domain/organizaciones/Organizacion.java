package domain.organizaciones;

import domain.database.PersistenceEntity;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import domain.organizaciones.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.organizaciones.excepciones.ExcepcionNoExisteElSectorEnLaOrganizacion;
import domain.organizaciones.miembros.Miembro;
import domain.notificaciones.contactos.Contacto;
import domain.organizaciones.datos.actividades.DatosActividades;
import domain.organizaciones.hc.HC;
import domain.organizaciones.hc.UnidadHC;
import domain.organizaciones.sectores.Sector;
import domain.ubicaciones.sectores.Localidad;
import domain.ubicaciones.sectores.Municipio;
import domain.ubicaciones.sectores.Provincia;
import domain.ubicaciones.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter // <-- Esto ya da getters para todoo lo de adentro
@Entity
public class Organizacion extends PersistenceEntity {
  private String nombreOrg;
  private String razonSocial;

  @Enumerated(EnumType.STRING)
  private TipoOrganizacion tipo;

  @Enumerated(EnumType.STRING)
  private ClasificacionOrg clasificacion;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "ubicacion_id")
  private Ubicacion ubicacion;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) @JoinColumn(name = "org_id")
  private List<Sector> sectores = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "org_id")
  @Setter private List<DatosActividades> datosActividades = new ArrayList<>();

  @Transient
  private List<Contacto> contactos = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "org_id")
  private List<HC> historialHCMensual = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "org_id")
  private List<HC> historialHCAnual = new ArrayList<>();

  public Organizacion() {}

  public Organizacion(String nombreOrg, String razonSocial, TipoOrganizacion tipo,
                      Ubicacion ubicacion, ClasificacionOrg clasificacion) {
    this.nombreOrg = nombreOrg;
    this.razonSocial = razonSocial;
    this.tipo = tipo;
    this.ubicacion = ubicacion;
    this.clasificacion = clasificacion;
  }

  // ------------- 1era entrega ---------------------------------

  public boolean containsSector(Sector sector) {
    return sectores.contains(sector);
  }

  // TODO: Quedarse con un solo 'containsMiembro'
  public boolean contieneMiembro(Miembro miembro) {
    return sectores.stream().anyMatch(sector -> sector.containsMiembro(miembro));
  }
  public boolean containsMiembro(Miembro miembro) { return sectores.stream().flatMap(sector -> sector.getMiembros().stream()).collect(Collectors.toList()).contains(miembro); }
  //

  public void agregarSector(Sector sector) {
    sectores.add(sector);
  }

  public void aceptarVinculacionDeTrabajador(Miembro miembro, Sector sector) {
    if (sector.containsMiembroParaAceptar(miembro) && this.containsSector(sector)) {
      sector.sacarMiembroParaAceptar(miembro);
      sector.agregarMiembro(miembro);
    } else if(!this.containsSector(sector)) {
      throw new ExcepcionNoExisteElSectorEnLaOrganizacion();
    } else { //if(!sector.containsMiembroParaAceptar(miembro))
      throw new ExcepcionNoExisteElMiembroAacptarEnLaOrg();
    }
  }

  // ------------- 2da entrega ---------------------------------

  public void cargarMediciones(String pathCSV) {
    String linea;

    //datosActividades.clear();
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
    Collections.addAll(contactos, nuevosContactos);
  }

  public List<Contacto> getContactos() {
    return contactos;
  }

  // Nota: En lo que aca llamamos 'combustible', se tiene en cuenta la distancia (en Tramo: combustible x dist)
  public void cargarDATransladoMiembros(){
    double combustibleTransporteMiembros = 30 * sectores.stream().mapToDouble(Sector::combustibleConsumidoTransporteMiembros).sum();
    //SimpleDateFormat formatFecha = new SimpleDateFormat("MM/yyyy");
    DateTimeFormatter formatFecha = DateTimeFormatter.ofPattern("MM/yyyy");
    // Multiplico por 30, para obtener el valor mensual dado que el trayecto que recorren los miembros es a diario

    DatosActividades datos = new DatosActividades("Distancia media",
        String.valueOf(combustibleTransporteMiembros),
        "Mensual",
        formatFecha.format(LocalDate.now()));
    if (combustibleTransporteMiembros > 0.0)
      datosActividades.add(datos);
  }
/*
    private double calculoHCMensual(){
      //this.cargarDATransladoMiembros();
      return datosActividades.stream().mapToDouble(DatosActividades::impactoHC).sum();
    }

    public HC hcMensual(){
      double hcDatosActividad = calculoHCMensual();
      HC hc = new HC(hcDatosActividad, UnidadHC.kgCO2);
      if(historialHC.stream().filter(hc1 -> hc1.enKgCO2() == hcDatosActividad).count() == 0)
        this.historialHC.add(hc);
      return hc;
    }
  */
  public HC hcMensual(String stringMes){

    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/yyyy");
    YearMonth mes = YearMonth.parse(stringMes, formatter2);

    double valoresMensuales = datosActividades.stream().filter(datosActividad -> datosActividad.getPeriodicidad().equals("Mensual")).filter(datosActividad -> (YearMonth.parse(datosActividad.getPeriodoImputacion(), formatter2).equals(mes))).mapToDouble(DatosActividades::impactoHC).sum();
    HC hc = new HC(valoresMensuales, UnidadHC.kgCO2);

    historialHCMensual.add(hc);
    return hc;
  }
/*
  public HC hcAnual(){
    double hcDatosActividad = calculoHCMensual() * 12;
    return new HC(hcDatosActividad, UnidadHC.kgCO2);
  }
*/
  public HC hcAnual(String stringAnio){

    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/yyyy");
    Year Anio = Year.parse(stringAnio, formatter1);
    YearMonth desdeMes = YearMonth.parse("01/"+stringAnio, formatter2).minusMonths(1);
    YearMonth hastaMes = YearMonth.parse("12/"+stringAnio, formatter2).plusMonths(1);

    double valoresAnuales = datosActividades.stream().filter(datosActividad -> datosActividad.getPeriodicidad().equals("Anual")).filter(datosActividad -> (Year.parse(datosActividad.getPeriodoImputacion(), formatter1).equals(Anio))).mapToDouble(DatosActividades::impactoHC).sum();
    double valoresMensuales = datosActividades.stream().filter(datosActividad -> datosActividad.getPeriodicidad().equals("Mensual")).filter(datosActividad -> (YearMonth.parse(datosActividad.getPeriodoImputacion(), formatter2).isAfter(desdeMes)
        && YearMonth.parse(datosActividad.getPeriodoImputacion(), formatter2).isBefore(hastaMes))).mapToDouble(DatosActividades::impactoHC).sum();

    HC hc = new HC(valoresAnuales+valoresMensuales, UnidadHC.kgCO2);

    historialHCAnual.add(hc);
    return hc;
  }

  public HC hcTotal(){
    double valorTotal = datosActividades.stream().mapToDouble(DatosActividades::impactoHC).sum();
    return new HC(valorTotal, UnidadHC.kgCO2);
  }

  public double composicionHCMensual(String tipo){
    HC hcTotal = this.hcTotal();

    double valorTipoConsumo = datosActividades.stream().filter(datosActividad -> datosActividad.getTipoDeConsumo().getTipo().equals(tipo)).mapToDouble(DatosActividades::impactoHC).sum();
    double porcentaje = 100*(valorTipoConsumo / hcTotal.enKgCO2());

    return porcentaje;
  }
}
