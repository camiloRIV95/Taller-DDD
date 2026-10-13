package com.rica.investigadoresservice.investigadores.infraestructura.salida.mensajeria;

import com.rica.investigadoresservice.investigadores.aplicacion.PublicadorEventos;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Component
public class OutboxPublicadorEventos implements PublicadorEventos {
  private final OutboxEventRepository repository;
  private final ObjectMapper objectMapper;
  public OutboxPublicadorEventos(OutboxEventRepository repository, ObjectMapper objectMapper) { this.repository = repository; this.objectMapper = objectMapper; }
  @Override @Transactional
  public void publicar(String topico, String clave, Map<String, Object> contenido) {
    try {
      repository.save(new OutboxEvent(topico, clave, objectMapper.writeValueAsString(contenido)));
    } catch (Exception e) {
      throw new IllegalStateException("No se pudo serializar el evento de dominio", e);
    }
  }
}
