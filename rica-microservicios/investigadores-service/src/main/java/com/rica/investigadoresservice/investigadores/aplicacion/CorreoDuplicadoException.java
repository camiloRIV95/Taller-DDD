package com.rica.investigadoresservice.investigadores.aplicacion;

public class CorreoDuplicadoException extends RuntimeException {
  public CorreoDuplicadoException(String correo) { super("Ya existe un investigador registrado con el correo " + correo); }
}
