# RICA API — Taller DDD

Implementación independiente del taller de DDD con Spring Boot 3, Java 17, PostgreSQL para investigadores y MongoDB para publicaciones.

El taller de comunicación entre microservicios está en [`rica-microservicios/`](rica-microservicios/README.md) y en la rama `taller-comunicacion`.

## Requisitos

- JDK 17 o superior
- Maven 3.9+
- Docker con Docker Compose

## Ejecutar

```sh
docker compose up -d
mvn spring-boot:run
```

La API queda en `http://localhost:8080`. Ejecuta las pruebas con `mvn test`.

## Ejemplos

```http
POST /api/investigadores
Content-Type: application/json

{"nombreCompleto":"Ana Torres","correoInstitucional":"ana.torres@uptc.edu.co","grupoInvestigacion":"GIT-UPTC"}
```

```http
POST /api/publicaciones
Content-Type: application/json

{"titulo":"DDD aplicado a RICA","investigadorCorreo":"ana.torres@uptc.edu.co","anio":2026,"revista":"Revista RICA"}
```

Cada investigador puede registrar hasta cinco publicaciones por año. El dominio valida el correo institucional al crear el Value Object y la Factory verifica duplicados.

## Patrones del taller

- Value Object: `CorreoInstitucional`
- Servicio de dominio: `LimitePublicacionesAnualesService`
- Agregados pequeños: `Investigador` no contiene una colección de publicaciones
- Factory: `InvestigadorFactory`
- Documento de lenguaje ubicuo y límite del agregado: [`NOTAS.md`](NOTAS.md)

## Alcance

El repositorio recibido no contenía código de partida. Por ello esta es una implementación nueva basada en el enunciado; las rutas y respuestas del Tutorial 6 pueden diferir. Revisa esa compatibilidad con el código original antes de presentarla como refactor equivalente.
