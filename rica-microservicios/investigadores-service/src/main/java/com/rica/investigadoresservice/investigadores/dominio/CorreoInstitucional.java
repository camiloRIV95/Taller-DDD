package com.rica.investigadoresservice.investigadores.dominio;

import jakarta.persistence.Embeddable;
import java.util.Locale;

@Embeddable
public record CorreoInstitucional(String valor) {
  public CorreoInstitucional {
    if (valor == null || !valor.trim().toLowerCase(Locale.ROOT).matches("^[^\\s@]+@uptc\\.edu\\.co$"))
      throw new IllegalArgumentException("El correo institucional debe pertenecer al dominio @uptc.edu.co");
    valor = valor.trim().toLowerCase(Locale.ROOT);
  }
}
