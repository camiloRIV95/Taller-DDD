package com.rica.investigadoresservice.investigadores.infraestructura.entrada.web;

import com.rica.investigadoresservice.investigadores.aplicacion.CorreoDuplicadoException;
import com.rica.investigadoresservice.investigadores.aplicacion.InvestigadorUseCase;
import com.rica.investigadoresservice.investigadores.aplicacion.RecursoNoEncontradoException;
import com.rica.investigadoresservice.investigadores.dominio.Investigador;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController @RequestMapping("/api/investigadores")
public class InvestigadorController {
  private final InvestigadorUseCase service;
  public InvestigadorController(InvestigadorUseCase service) { this.service = service; }
  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public InvestigadorResponse registrar(@Valid @RequestBody InvestigadorRequest request) {
    return response(service.registrar(request.nombreCompleto(), request.correoInstitucional(), request.grupoInvestigacion()));
  }
  @GetMapping public List<InvestigadorResponse> listar() { return service.listar().stream().map(InvestigadorController::response).toList(); }
  @GetMapping("/{id}") public InvestigadorResponse obtener(@PathVariable Long id) { return response(service.buscarPorId(id)); }
  @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.iniciarEliminacion(id); return ResponseEntity.accepted().build(); }
  private static InvestigadorResponse response(Investigador i) { return new InvestigadorResponse(i.getId(), i.getNombreCompleto(), i.getCorreoInstitucional().valor(), i.getGrupoInvestigacion(), i.getEstado().name()); }
  public record InvestigadorRequest(@NotBlank String nombreCompleto, @NotBlank String correoInstitucional, @NotBlank String grupoInvestigacion) { }
  public record InvestigadorResponse(Long id, String nombreCompleto, String correoInstitucional, String grupoInvestigacion, String estado) { }
}

@RestControllerAdvice
class InvestigadorExceptionHandler {
  @ExceptionHandler(CorreoDuplicadoException.class) ResponseEntity<ErrorResponse> duplicado(CorreoDuplicadoException e) { return ResponseEntity.status(409).body(new ErrorResponse(e.getMessage())); }
  @ExceptionHandler(RecursoNoEncontradoException.class) ResponseEntity<ErrorResponse> noEncontrado(RecursoNoEncontradoException e) { return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage())); }
  @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<ErrorResponse> invalido(IllegalArgumentException e) { return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage())); }
  record ErrorResponse(String error) { }
}
