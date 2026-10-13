package com.rica.ricaapi;

import com.rica.ricaapi.investigadores.CorreoDuplicadoException;
import com.rica.ricaapi.investigadores.RecursoNoEncontradoException;
import com.rica.ricaapi.publicaciones.LimiteAnualExcedidoException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<Map<String, String>> solicitudInvalida(Exception ex) { return error(HttpStatus.BAD_REQUEST, ex.getMessage()); }
  @ExceptionHandler({CorreoDuplicadoException.class, LimiteAnualExcedidoException.class})
  public ResponseEntity<Map<String, String>> conflicto(Exception ex) { return error(HttpStatus.CONFLICT, ex.getMessage()); }
  @ExceptionHandler(RecursoNoEncontradoException.class)
  public ResponseEntity<Map<String, String>> noEncontrado(Exception ex) { return error(HttpStatus.NOT_FOUND, ex.getMessage()); }
  private ResponseEntity<Map<String, String>> error(HttpStatus status, String message) {
    return ResponseEntity.status(status).body(Map.of("error", message == null ? status.getReasonPhrase() : message));
  }
}
