package domain;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.hc.HC;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import domain.repositorios.RepositorioOrganizaciones;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.sectores.AgenteSectorial;
import domain.ubicaciones.sectores.TipoSectorTerritorial;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkspaceRepos {

  public static void main(String[] args) throws IOException {

    // FULL CON REPOS

    /*Ubicacion ubicacion = new Ubicacion("rivadavia", 2000, "ARGENTINA",
        "BUENOS AIRES",
        "AVELLANEDA",
        "AVELLANEDA");
    ubicacion.getLocalidad();*/

    int index = 1;

    List<String> strings = new ArrayList<>();
    strings.add("Hola");
    strings.add("Galo");
    strings.add("Hol");
    strings.add("Gala");
    strings.add("Gal");

    int id = 154;
    String stri = String.valueOf(id);
    strings.add(stri);

    Collections.sort(strings);
    strings.forEach(System.out::println);

    AgenteSectorial agenteSectorial = new AgenteSectorial(TipoSectorTerritorial.PROVINCIA, 168);

    HC hc = agenteSectorial.hcSectorMensual();

    System.out.println(hc.enKgCO2());

  }

}
