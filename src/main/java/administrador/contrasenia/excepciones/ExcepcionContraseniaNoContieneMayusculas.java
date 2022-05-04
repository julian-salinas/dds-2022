package administrador.contrasenia.excepciones;

public class ExcepcionContraseniaNoContieneMayusculas extends RuntimeException {
  public ExcepcionContraseniaNoContieneMayusculas() {
    super("Tu contraseña debe contener mayusculas");
  }
}

