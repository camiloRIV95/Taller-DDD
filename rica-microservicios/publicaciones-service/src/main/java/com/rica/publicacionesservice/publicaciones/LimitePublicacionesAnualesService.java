package com.rica.publicacionesservice.publicaciones;
import org.springframework.stereotype.Service;
@Service
public class LimitePublicacionesAnualesService {
  public static final int MAXIMO_POR_ANIO = 5;
  private final PublicacionRepository repository;
  public LimitePublicacionesAnualesService(PublicacionRepository repository) { this.repository = repository; }
  public boolean puedeRegistrar(Publicacion nueva) { return repository.countByInvestigadorCorreoAndAnio(nueva.getInvestigadorCorreo(), nueva.getAnio()) < MAXIMO_POR_ANIO; }
}
