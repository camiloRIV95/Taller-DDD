package com.rica.publicacionesservice.publicaciones;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class PublicacionExceptionHandler {
  @ExceptionHandler(InvestigadorNoExisteException.class) ResponseEntity<Map<String, String>> noExiste(InvestigadorNoExisteException e) { return error(HttpStatus.NOT_FOUND, e); }
  @ExceptionHandler(ServicioNoDisponibleException.class) ResponseEntity<Map<String, String>> noDisponible(ServicioNoDisponibleException e) { return error(HttpStatus.SERVICE_UNAVAILABLE, e); }
  @ExceptionHandler(LimiteAnualExcedidoException.class) ResponseEntity<Map<String, String>> limite(LimiteAnualExcedidoException e) { return error(HttpStatus.CONFLICT, e); }
  @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<Map<String, String>> invalido(IllegalArgumentException e) { return error(HttpStatus.BAD_REQUEST, e); }
  private ResponseEntity<Map<String, String>> error(HttpStatus status, Exception e) { return ResponseEntity.status(status).body(Map.of("error", e.getMessage())); }
}
