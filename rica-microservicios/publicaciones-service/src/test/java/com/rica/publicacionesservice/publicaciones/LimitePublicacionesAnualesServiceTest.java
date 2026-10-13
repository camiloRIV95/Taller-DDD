package com.rica.publicacionesservice.publicaciones;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class LimitePublicacionesAnualesServiceTest {
  @Mock PublicacionRepository repository;
  @InjectMocks LimitePublicacionesAnualesService service;
  @Test void permiteHastaCuatroPublicacionesPrevias() {
    Publicacion nueva = new Publicacion("Nueva", "ana@uptc.edu.co", 2025, null);
    when(repository.countByInvestigadorCorreoAndAnio("ana@uptc.edu.co", 2025)).thenReturn(4L);
    assertTrue(service.puedeRegistrar(nueva));
  }
  @Test void bloqueaLaSextaPublicacionDelAnio() {
    Publicacion nueva = new Publicacion("Nueva", "ana@uptc.edu.co", 2025, null);
    when(repository.countByInvestigadorCorreoAndAnio("ana@uptc.edu.co", 2025)).thenReturn(5L);
    assertFalse(service.puedeRegistrar(nueva));
  }
}
