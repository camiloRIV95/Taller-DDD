package com.rica.ricaapi.investigadores;

import org.springframework.stereotype.Component;

@Component
public class InvestigadorFactory {
  private final InvestigadorRepository investigadorRepository;
  public InvestigadorFactory(InvestigadorRepository investigadorRepository) { this.investigadorRepository = investigadorRepository; }

  public Investigador crear(String nombreCompleto, String correoInstitucional, String grupoInvestigacion) {
    String nombre = requerido(nombreCompleto, "El nombre completo es obligatorio");
    String grupo = requerido(grupoInvestigacion, "El grupo de investigación es obligatorio");
    CorreoInstitucional correo = new CorreoInstitucional(correoInstitucional);
    if (investigadorRepository.existsByCorreoInstitucional_Valor(correo.valor())) {
      throw new CorreoDuplicadoException("Ya existe un investigador registrado con el correo " + correo.valor());
    }
    return new Investigador(null, nombre, correo, grupo);
  }
  private String requerido(String value, String message) {
    if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
    return value.trim();
  }
}
