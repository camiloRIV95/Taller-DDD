package com.rica.publicacionesservice.publicaciones;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
class PublicacionTest {
  @Test void permiteMarcarPublicacionHuerfana() {
    Publicacion publicacion = new Publicacion("Título", "ana@uptc.edu.co", 2025, "Revista");
    assertFalse(publicacion.isHuerfana());
    publicacion.marcarComoHuerfana();
    assertTrue(publicacion.isHuerfana());
  }
  @Test void rechazaAnioFuturo() { assertThrows(IllegalArgumentException.class, () -> new Publicacion("Título", "ana@uptc.edu.co", 2999, null)); }
}
