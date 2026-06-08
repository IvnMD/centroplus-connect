package es.ies.puerto.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IncidenciaTest {

    private Incidencia incidencia;

    @BeforeEach
    void setUp() {
        incidencia = new Incidencia(1L, 1L, "Asunto test", "Descripcion test", "2026-06-08");
    }

    @Test
    @Order(1)
    @DisplayName("Constructor con todos los campos")
    void constructorTestOk() {
        assertNotNull(incidencia);
    }

    @Test
    @Order(2)
    @DisplayName("getAsunto devuelve el valor correcto")
    void getAsuntoTestOk() {
        assertEquals("Asunto test", incidencia.getAsunto());
    }

    @Test
    @Order(3)
    @DisplayName("getDescripcion devuelve el valor correcto")
    void getDescripcionTestOk() {
        assertEquals("Descripcion test", incidencia.getDescripcion());
    }

    @Test
    @Order(4)
    @DisplayName("getFecha devuelve el valor correcto")
    void getFechaTestOk() {
        assertEquals("2026-06-08", incidencia.getFecha());
    }

    @Test
    @Order(5)
    @DisplayName("getIdUsuario devuelve el valor correcto")
    void getIdUsuarioTestOk() {
        assertEquals(1L, incidencia.getIdUsuario());
    }

    @Test
    @Order(6)
    @DisplayName("equals con misma id es true")
    void equalsTestOk() {
        Incidencia otra = new Incidencia(1L);
        assertEquals(incidencia, otra);
    }

    @Test
    @Order(7)
    @DisplayName("equals con distinta id es false")
    void equalsTestFalse() {
        Incidencia otra = new Incidencia(99L);
        assertNotEquals(incidencia, otra);
    }

    @Test
    @Order(8)
    @DisplayName("toString no es null ni vacio")
    void toStringTestOk() {
        assertNotNull(incidencia.toString());
        assertFalse(incidencia.toString().isEmpty());
    }
}