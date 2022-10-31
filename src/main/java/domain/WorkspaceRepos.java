package domain;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import domain.repositorios.RepositorioOrganizaciones;
import domain.ubicaciones.Ubicacion;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

public class WorkspaceRepos {

  public static void main(String[] args) throws IOException {

    // FULL CON REPOS

    Ubicacion ubicacion = new Ubicacion("rivadavia", 2000, "ARGENTINA",
        "BUENOS AIRES",
        "AVELLANEDA",
        "AVELLANEDA");
    ubicacion.getLocalidad();

  }

}
