package com.rica.publicacionesservice.publicaciones;
import java.time.Year;
import java.util.Locale;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "publicaciones")
@CompoundIndex(name = "investigador_anio_idx", def = "{'investigadorCorreo': 1, 'anio': 1}")
public class Publicacion {
  @Id private String id;
  private String titulo;
  private String investigadorCorreo;
  private Integer anio;
  private String revista;
  private boolean huerfana;
  protected Publicacion() { }
  public Publicacion(String titulo, String investigadorCorreo, Integer anio, String revista) {
    if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("El título es obligatorio");
    if (investigadorCorreo == null || investigadorCorreo.isBlank()) throw new IllegalArgumentException("El correo del investigador es obligatorio");
    if (anio == null || anio < 1900 || anio > Year.now().getValue()) throw new IllegalArgumentException("El año de publicación no es válido");
    this.titulo = titulo.trim(); this.investigadorCorreo = investigadorCorreo.trim().toLowerCase(Locale.ROOT);
    this.anio = anio; this.revista = revista == null ? null : revista.trim();
  }
  public String getId() { return id; }
  public String getTitulo() { return titulo; }
  public String getInvestigadorCorreo() { return investigadorCorreo; }
  public Integer getAnio() { return anio; }
  public String getRevista() { return revista; }
  public boolean isHuerfana() { return huerfana; }
  public void marcarComoHuerfana() { huerfana = true; }
}
