package com.rica.ricaapi.investigadores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InvestigadorFactoryTest {
  @Mock InvestigadorRepository repository;
  @InjectMocks InvestigadorFactory factory;

  @Test void creaInvestigadorConCorreoValidado() {
    when(repository.existsByCorreoInstitucional_Valor("ana@uptc.edu.co")).thenReturn(false);
    Investigador investigador = factory.crear("Ana Torres", "ana@uptc.edu.co", "GIT-UPTC");
    assertEquals("Ana Torres", investigador.getNombreCompleto());
    assertEquals("ana@uptc.edu.co", investigador.getCorreoInstitucional().valor());
    verify(repository).existsByCorreoInstitucional_Valor("ana@uptc.edu");
  }
  @Test void rechazaCorreoDuplicado() {
    when(repository.existsByCorreoInstitucional_Valor("ana@uptc.edu.co")).thenReturn(true);
    assertThrows(CorreoDuplicadoException.class, () -> factory.crear("Ana Torres", "ana@uptc.edu.co", "GIT-UPTC"));
  }
}
