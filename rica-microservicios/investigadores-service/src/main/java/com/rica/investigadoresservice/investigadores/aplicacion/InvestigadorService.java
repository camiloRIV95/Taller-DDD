package com.rica.investigadoresservice.investigadores.aplicacion;

import com.rica.investigadoresservice.investigadores.dominio.Investigador;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvestigadorService implements InvestigadorUseCase {
  private final RepositorioInvestigadores repositorio;
  private final InvestigadorFactory factory;
  private final PublicadorEventos publicador;
  public InvestigadorService(RepositorioInvestigadores repositorio, InvestigadorFactory factory, PublicadorEventos publicador) {
    this.repositorio = repositorio; this.factory = factory; this.publicador = publicador;
  }
  @Override @Transactional
  public Investigador registrar(String nombre, String correo, String grupo) {
    Investigador guardado = repositorio.guardar(factory.crear(nombre, correo, grupo));
    String correoNormalizado = guardado.getCorreoInstitucional().valor();
    publicador.publicar("investigadores.registrados", correoNormalizado,
        Map.of("id", guardado.getId(), "correoInstitucional", correoNormalizado, "nombreCompleto", guardado.getNombreCompleto()));
    return guardado;
  }
  @Override @Transactional(readOnly = true) public List<Investigador> listar() { return repositorio.listar(); }
  @Override @Transactional(readOnly = true) public Investigador buscarPorId(Long id) {
    return repositorio.buscarPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No existe el investigador " + id));
  }
  @Override @Transactional public void iniciarEliminacion(Long id) {
    Investigador investigador = buscarPorId(id);
    if (investigador.estaEnEliminacion()) return;
    investigador.marcarEnEliminacion(); repositorio.guardar(investigador);
    String correo = investigador.getCorreoInstitucional().valor();
    publicador.publicar("investigadores.en-eliminacion", correo, Map.of("correoInstitucional", correo));
  }
  @Override @Transactional public void confirmarEliminacion(String correo) {
    repositorio.buscarPorCorreo(correo).filter(Investigador::estaEnEliminacion).ifPresent(repositorio::eliminar);
  }
}
