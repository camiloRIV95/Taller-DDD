package com.rica.ricaapi.publicaciones;

import com.rica.ricaapi.investigadores.Investigador;
import org.springframework.stereotype.Service;

@Service
public class LimitePublicacionesAnualesService {
  public static final int MAXIMO_POR_ANIO = 5;
  private final PublicacionRepository publicacionRepository;
  public LimitePublicacionesAnualesService(PublicacionRepository publicacionRepository) { this.publicacionRepository = publicacionRepository; }
  public boolean puedeRegistrar(Investigador investigador, Publicacion nueva) {
    long registradas = publicacionRepository.countByInvestigadorCorreoAndAnio(investigador.getCorreoInstitucional().valor(), nueva.getAnio());
    return registradas < MAXIMO_POR_ANIO;
  }
}
