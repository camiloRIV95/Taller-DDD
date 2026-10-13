package com.rica.publicacionesservice.publicaciones;
public class InvestigadorNoExisteException extends RuntimeException { public InvestigadorNoExisteException(String correo) { super("No existe un investigador registrado con el correo " + correo); } }
