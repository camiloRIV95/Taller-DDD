package com.rica.investigadoresservice.investigadores.aplicacion;

import com.rica.investigadoresservice.investigadores.dominio.Investigador;
import java.util.List;

public interface InvestigadorUseCase {
  Investigador registrar(String nombreCompleto, String correoInstitucional, String grupoInvestigacion);
  List<Investigador> listar();
  Investigador buscarPorId(Long id);
  void iniciarEliminacion(Long id);
  void confirmarEliminacion(String correoInstitucional);
}
