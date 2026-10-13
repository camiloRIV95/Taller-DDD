package com.rica.ricaapi.publicaciones;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import com.rica.ricaapi.investigadores.CorreoInstitucional;
import com.rica.ricaapi.investigadores.Investigador;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LimitePublicacionesAnualesServiceTest {
  @Mock PublicacionRepository repository;
  @InjectMocks LimitePublicacionesAnualesService service;
  private final Investigador investigador = new Investigador(1L, "Ana Torres", new CorreoInstitucional("ana@uptc.edu.co"), "GIT-UPTC");
  private final Publicacion publicacion = new Publicacion("DDD aplicado", "ana@uptc.edu.co", 2026, "Revista RICA");

  @Test void permiteRegistrarMientrasNoSeAlcanceElMaximo() {
    when(repository.countByInvestigadorCorreoAndAnio("ana@uptc.edu.co", 2026)).thenReturn(4L);
    assertTrue(service.puedeRegistrar(investigador, publicacion));
  }
  @Test void rechazaAlLlegarAlMaximo() {
    when(repository.countByInvestigadorCorreoAndAnio("ana@uptc.edu.co", 2026)).thenReturn(5L);
    assertFalse(service.puedeRegistrar(investigador, publicacion));
  }
}
