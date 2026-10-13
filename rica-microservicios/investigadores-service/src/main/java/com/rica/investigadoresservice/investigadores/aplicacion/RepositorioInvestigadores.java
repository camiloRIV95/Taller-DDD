package com.rica.investigadoresservice.investigadores.aplicacion;

import com.rica.investigadoresservice.investigadores.dominio.Investigador;
import java.util.List;
import java.util.Optional;

public interface RepositorioInvestigadores {
  boolean existeCorreo(String correo);
  Investigador guardar(Investigador investigador);
  Optional<Investigador> buscarPorId(Long id);
  Optional<Investigador> buscarPorCorreo(String correo);
  List<Investigador> listar();
  void eliminar(Investigador investigador);
}
