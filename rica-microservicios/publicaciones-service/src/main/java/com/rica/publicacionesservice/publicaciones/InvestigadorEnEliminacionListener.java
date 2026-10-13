package com.rica.publicacionesservice.publicaciones;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
@Component
public class InvestigadorEnEliminacionListener {
  private static final Logger log = LoggerFactory.getLogger(InvestigadorEnEliminacionListener.class);
  private final PublicacionRepository repository;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  public InvestigadorEnEliminacionListener(PublicacionRepository repository, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) { this.repository = repository; this.kafkaTemplate = kafkaTemplate; this.objectMapper = objectMapper; }
  @KafkaListener(topics = "investigadores.en-eliminacion")
  public void alIniciarseEliminacion(String mensaje) {
    try {
      String correo = objectMapper.readTree(mensaje).get("correoInstitucional").asString();
      List<Publicacion> publicaciones = repository.findByInvestigadorCorreo(correo);
      publicaciones.forEach(Publicacion::marcarComoHuerfana);
      repository.saveAll(publicaciones);
      log.info("Saga: {} publicaciones de {} marcadas como huérfanas", publicaciones.size(), correo);
      String confirmacion = objectMapper.writeValueAsString(Map.of("correoInstitucional", correo, "cantidad", publicaciones.size()));
      kafkaTemplate.send("publicaciones.marcadas-huerfanas", correo, confirmacion);
    } catch (Exception e) { throw new IllegalArgumentException("No se pudo procesar el evento de eliminación", e); }
  }
}
