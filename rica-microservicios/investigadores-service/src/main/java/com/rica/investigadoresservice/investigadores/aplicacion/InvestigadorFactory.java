package com.rica.investigadoresservice.investigadores.aplicacion;

import com.rica.investigadoresservice.investigadores.dominio.CorreoInstitucional;
import com.rica.investigadoresservice.investigadores.dominio.Investigador;
import org.springframework.stereotype.Component;

@Component
public class InvestigadorFactory {
  private final RepositorioInvestigadores repositorio;
  public InvestigadorFactory(RepositorioInvestigadores repositorio) { this.repositorio = repositorio; }
  public Investigador crear(String nombreCompleto, String correoInstitucional, String grupoInvestigacion) {
    if (nombreCompleto == null || nombreCompleto.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
    if (grupoInvestigacion == null || grupoInvestigacion.isBlank()) throw new IllegalArgumentException("El grupo es obligatorio");
    CorreoInstitucional correo = new CorreoInstitucional(correoInstitucional);
    if (repositorio.existeCorreo(correo.valor())) throw new CorreoDuplicadoException(correo.valor());
    return new Investigador(nombreCompleto.trim(), correo, grupoInvestigacion.trim());
  }
}
