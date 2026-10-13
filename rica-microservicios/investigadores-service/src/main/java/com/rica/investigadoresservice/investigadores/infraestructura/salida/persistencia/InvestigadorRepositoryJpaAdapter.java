package com.rica.investigadoresservice.investigadores.infraestructura.salida.persistencia;

import com.rica.investigadoresservice.investigadores.aplicacion.RepositorioInvestigadores;
import com.rica.investigadoresservice.investigadores.dominio.Investigador;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class InvestigadorRepositoryJpaAdapter implements RepositorioInvestigadores {
  private final InvestigadorRepository repository;
  public InvestigadorRepositoryJpaAdapter(InvestigadorRepository repository) { this.repository = repository; }
  @Override public boolean existeCorreo(String correo) { return repository.existsByCorreoInstitucional_Valor(correo); }
  @Override public Investigador guardar(Investigador investigador) { return repository.save(investigador); }
  @Override public Optional<Investigador> buscarPorId(Long id) { return repository.findById(id); }
  @Override public Optional<Investigador> buscarPorCorreo(String correo) { return repository.findByCorreoInstitucional_Valor(correo); }
  @Override public List<Investigador> listar() { return repository.findAll(); }
  @Override public void eliminar(Investigador investigador) { repository.delete(investigador); }
}
