package entrega3;

import domain.notificaciones.contactos.Contacto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactoTest {

    @Test
    void instanciarUnContactoValido() {
        assertDoesNotThrow(() -> new Contacto("pepita@mail.com", "5491112341234"));
    }

    @Test
    void contactoConMailInvalido() {
        assertThrows(RuntimeException.class, () -> new Contacto("nombre+apellido@mail.com", "5491112341234"));
    }

    @Test
    void contactoConWhatsappInvalido() {
        assertThrows(RuntimeException.class, () -> new Contacto("pepita@mail.com", "1112341234"));
    }

    @Test
    void contactoConDatoDeContactoInvalido() {
        assertThrows(RuntimeException.class, () -> new Contacto("1112341234"));
        assertThrows(RuntimeException.class, () -> new Contacto("persona@mail"));
    }

    @Test()
    @DisplayName("Cuando se instancia un contacto con un solo dato de contacto, se almacena en el atributo correcto")
    void setDatoDeContactoCorrecto() {
        Contacto unContacto, otroContacto;
        unContacto = new Contacto("pepita@mail.com");
        otroContacto = new Contacto("5491112341234");

        assertEquals(unContacto.getEmail(), "pepita@mail.com");
        assertEquals(unContacto.getWhatsApp(), null);

        assertEquals(otroContacto.getEmail(), null);
        assertEquals(otroContacto.getWhatsApp(), "5491112341234");
    }
}
