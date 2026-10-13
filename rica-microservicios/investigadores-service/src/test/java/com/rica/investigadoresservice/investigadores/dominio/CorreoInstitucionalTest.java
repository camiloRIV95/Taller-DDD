package com.rica.investigadoresservice.investigadores.dominio;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
class CorreoInstitucionalTest {
  @Test void normalizaCorreoInstitucional() { assertEquals("ana@uptc.edu.co", new CorreoInstitucional(" ANA@UPTC.EDU.CO ").valor()); }
  @Test void rechazaDominioAjeno() { assertThrows(IllegalArgumentException.class, () -> new CorreoInstitucional("ana@gmail.com")); }
}
