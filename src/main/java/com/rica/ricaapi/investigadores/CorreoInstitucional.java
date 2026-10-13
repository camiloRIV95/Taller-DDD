package com.rica.ricaapi.investigadores;

import jakarta.persistence.Embeddable;
import java.util.Locale;
import java.util.Objects;

@Embeddable
public record CorreoInstitucional(String valor) {
  public CorreoInstitucional {
    if (valor == null || !valor.trim().toLowerCase(Locale.ROOT).endsWith("@uptc.edu.co")) {
      throw new IllegalArgumentException("El correo institucional debe pertenecer al dominio @uptc.edu.co");
    }
    valor = valor.trim().toLowerCase(Locale.ROOT);
    if (!valor.matches("^[^\\s@]+@uptc\\.edu\\.co$")) {
      throw new IllegalArgumentException("El correo institucional no tiene un formato válido");
    }
  }
}
