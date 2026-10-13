package com.rica.publicacionesservice.publicaciones;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
@Service
public class PublicacionService {
  private final PublicacionRepository repository;
  private final VerificadorInvestigador verificador;
  private final LimitePublicacionesAnualesService limite;
  public PublicacionService(PublicacionRepository repository, VerificadorInvestigador verificador, LimitePublicacionesAnualesService limite) { this.repository = repository; this.verificador = verificador; this.limite = limite; }
  public Publicacion registrar(String titulo, String correo, Integer anio, String revista) {
    String normalizado = correo.trim().toLowerCase(Locale.ROOT);
    if (!verificador.existe(normalizado)) throw new InvestigadorNoExisteException(normalizado);
    Publicacion nueva = new Publicacion(titulo, normalizado, anio, revista);
    if (!limite.puedeRegistrar(nueva)) throw new LimiteAnualExcedidoException("El investigador ya alcanzó el máximo de publicaciones registradas para " + anio);
    return repository.save(nueva);
  }
  public List<Publicacion> listarPorInvestigador(String correo) { return repository.findByInvestigadorCorreo(correo.trim().toLowerCase(Locale.ROOT)); }
}
