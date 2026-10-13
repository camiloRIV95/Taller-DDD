package com.rica.ricaapi.publicaciones;

import com.rica.ricaapi.investigadores.Investigador;
import com.rica.ricaapi.investigadores.InvestigadorService;
import com.rica.ricaapi.investigadores.RecursoNoEncontradoException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PublicacionService {
  private final PublicacionRepository publicacionRepository;
  private final InvestigadorService investigadorService;
  private final LimitePublicacionesAnualesService limiteService;
  public PublicacionService(PublicacionRepository publicacionRepository, InvestigadorService investigadorService, LimitePublicacionesAnualesService limiteService) {
    this.publicacionRepository = publicacionRepository; this.investigadorService = investigadorService; this.limiteService = limiteService;
  }
  public Publicacion registrar(String titulo, String investigadorCorreo, Integer anio, String revista) {
    Investigador investigador = investigadorService.obtenerPorCorreo(investigadorCorreo);
    Publicacion nueva = new Publicacion(titulo, investigador.getCorreoInstitucional().valor(), anio, revista);
    if (!limiteService.puedeRegistrar(investigador, nueva)) {
      throw new LimiteAnualExcedidoException("El investigador ya alcanzó el máximo de " + LimitePublicacionesAnualesService.MAXIMO_POR_ANIO + " publicaciones en " + anio);
    }
    return publicacionRepository.save(nueva);
  }
  public List<Publicacion> listarPorInvestigador(String correo) {
    Investigador investigador = investigadorService.obtenerPorCorreo(correo);
    return publicacionRepository.findByInvestigadorCorreo(investigador.getCorreoInstitucional().valor());
  }
}
