package com.rica.ricaapi.investigadores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class CorreoInstitucionalTest {
  @Test void normalizaCorreoInstitucionalValido() {
    assertEquals("ana.torres@uptc.edu.co", new CorreoInstitucional(" Ana.Torres@UPTC.EDU.CO ").valor());
  }
  @Test void rechazaCorreoDeDominioNoInstitucional() {
    assertThrows(IllegalArgumentException.class, () -> new CorreoInstitucional("carlos@gmail.com"));
  }
  @Test void rechazaCorreoNulo() {
    assertThrows(IllegalArgumentException.class, () -> new CorreoInstitucional(null));
  }
}
