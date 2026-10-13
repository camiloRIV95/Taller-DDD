package com.rica.ricaapi.investigadores;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investigadores")
public class InvestigadorController {
  private final InvestigadorService investigadorService;
  public InvestigadorController(InvestigadorService investigadorService) { this.investigadorService = investigadorService; }
  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public InvestigadorResponse registrar(@Valid @RequestBody InvestigadorRequest request) {
    return InvestigadorResponse.desde(investigadorService.registrar(request.nombreCompleto(), request.correoInstitucional(), request.grupoInvestigacion()));
  }
  @GetMapping public List<InvestigadorResponse> listar() { return investigadorService.listar().stream().map(InvestigadorResponse::desde).toList(); }
  @GetMapping("/{id}") public InvestigadorResponse obtener(@PathVariable Long id) { return InvestigadorResponse.desde(investigadorService.obtener(id)); }
  public record InvestigadorRequest(@NotBlank String nombreCompleto, @NotBlank String correoInstitucional, @NotBlank String grupoInvestigacion) { }
  public record InvestigadorResponse(Long id, String nombreCompleto, String correoInstitucional, String grupoInvestigacion) {
    static InvestigadorResponse desde(Investigador i) { return new InvestigadorResponse(i.getId(), i.getNombreCompleto(), i.getCorreoInstitucional().valor(), i.getGrupoInvestigacion()); }
  }
}
