package com.rica.ricaapi.investigadores;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvestigadorService {
  private final InvestigadorFactory investigadorFactory;
  private final InvestigadorRepository investigadorRepository;
  public InvestigadorService(InvestigadorFactory investigadorFactory, InvestigadorRepository investigadorRepository) {
    this.investigadorFactory = investigadorFactory;
    this.investigadorRepository = investigadorRepository;
  }
  public Investigador registrar(String nombreCompleto, String correoInstitucional, String grupoInvestigacion) {
    return investigadorRepository.save(investigadorFactory.crear(nombreCompleto, correoInstitucional, grupoInvestigacion));
  }
  @Transactional(readOnly = true)
  public List<Investigador> listar() { return investigadorRepository.findAll(); }
  @Transactional(readOnly = true)
  public Investigador obtener(Long id) {
    return investigadorRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("No existe el investigador " + id));
  }
  @Transactional(readOnly = true)
  public Investigador obtenerPorCorreo(String correo) {
    return investigadorRepository.findByCorreoInstitucional_Valor(new CorreoInstitucional(correo).valor())
        .orElseThrow(() -> new RecursoNoEncontradoException("No existe el investigador " + correo));
  }
}
