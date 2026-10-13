package com.rica.investigadoresservice.investigadores.infraestructura.entrada.mensajeria;

import com.rica.investigadoresservice.investigadores.aplicacion.InvestigadorUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PublicacionesMarcadasListener {
  private static final Logger log = LoggerFactory.getLogger(PublicacionesMarcadasListener.class);
  private final InvestigadorUseCase service;
  private final ObjectMapper mapper;
  public PublicacionesMarcadasListener(InvestigadorUseCase service, ObjectMapper mapper) { this.service = service; this.mapper = mapper; }
  @KafkaListener(topics = "publicaciones.marcadas-huerfanas")
  public void alMarcarsePublicaciones(String mensaje) {
    try {
      String correo = mapper.readTree(mensaje).get("correoInstitucional").asString();
      log.info("Saga: publicaciones confirmó para {}. Eliminando investigador.", correo);
      service.confirmarEliminacion(correo);
    } catch (Exception e) { throw new IllegalArgumentException("No se pudo procesar la confirmación de la Saga", e); }
  }
}
