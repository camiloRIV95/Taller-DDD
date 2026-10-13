package com.rica.ricaapi.publicaciones;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {
  private final PublicacionService publicacionService;
  public PublicacionController(PublicacionService publicacionService) { this.publicacionService = publicacionService; }
  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public Publicacion registrar(@Valid @RequestBody PublicacionRequest request) { return publicacionService.registrar(request.titulo(), request.investigadorCorreo(), request.anio(), request.revista()); }
  @GetMapping public List<Publicacion> listar(@RequestParam String investigadorCorreo) { return publicacionService.listarPorInvestigador(investigadorCorreo); }
  public record PublicacionRequest(@NotBlank String titulo, @NotBlank String investigadorCorreo, @NotNull Integer anio, String revista) { }
}
