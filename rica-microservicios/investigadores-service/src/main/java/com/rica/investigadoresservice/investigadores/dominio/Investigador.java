package com.rica.investigadoresservice.investigadores.dominio;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "investigadores")
public class Investigador {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(name = "nombre_completo", nullable = false, length = 150) private String nombreCompleto;
  @Embedded @AttributeOverride(name = "valor", column = @Column(name = "correo_institucional", nullable = false, unique = true, length = 150))
  private CorreoInstitucional correoInstitucional;
  @Column(name = "grupo_investigacion", nullable = false, length = 120) private String grupoInvestigacion;
  @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private EstadoInvestigador estado = EstadoInvestigador.ACTIVO;

  protected Investigador() { }
  public Investigador(String nombreCompleto, CorreoInstitucional correoInstitucional, String grupoInvestigacion) {
    this.nombreCompleto = nombreCompleto; this.correoInstitucional = Objects.requireNonNull(correoInstitucional);
    this.grupoInvestigacion = grupoInvestigacion; this.estado = EstadoInvestigador.ACTIVO;
  }
  public Long getId() { return id; }
  public String getNombreCompleto() { return nombreCompleto; }
  public CorreoInstitucional getCorreoInstitucional() { return correoInstitucional; }
  public String getGrupoInvestigacion() { return grupoInvestigacion; }
  public EstadoInvestigador getEstado() { return estado; }
  public boolean estaEnEliminacion() { return estado == EstadoInvestigador.EN_ELIMINACION; }
  public void marcarEnEliminacion() { estado = EstadoInvestigador.EN_ELIMINACION; }
  @Override public boolean equals(Object other) { return this == other || other instanceof Investigador that && id != null && id.equals(that.id); }
  @Override public int hashCode() { return getClass().hashCode(); }
}
