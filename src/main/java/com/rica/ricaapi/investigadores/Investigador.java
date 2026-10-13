package com.rica.ricaapi.investigadores;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "investigadores")
public class Investigador {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "nombre_completo", nullable = false, length = 150)
  private String nombreCompleto;
  @Embedded
  @AttributeOverride(name = "valor", column = @Column(name = "correo_institucional", nullable = false, unique = true, length = 150))
  private CorreoInstitucional correoInstitucional;
  @Column(name = "grupo_investigacion", nullable = false, length = 120)
  private String grupoInvestigacion;

  protected Investigador() { }

  Investigador(Long id, String nombreCompleto, CorreoInstitucional correoInstitucional, String grupoInvestigacion) {
    this.id = id;
    this.nombreCompleto = nombreCompleto;
    this.correoInstitucional = Objects.requireNonNull(correoInstitucional);
    this.grupoInvestigacion = grupoInvestigacion;
  }

  public Long getId() { return id; }
  public String getNombreCompleto() { return nombreCompleto; }
  public CorreoInstitucional getCorreoInstitucional() { return correoInstitucional; }
  public String getGrupoInvestigacion() { return grupoInvestigacion; }

  @Override public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof Investigador that)) return false;
    return id != null && id.equals(that.id);
  }
  @Override public int hashCode() { return getClass().hashCode(); }
}
