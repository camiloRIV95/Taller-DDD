# RICA Microservicios — Taller de comunicación

Implementación en rama independiente del taller de Lección 6. Conserva la API monolítica del taller DDD en la raíz del repositorio y agrega esta arquitectura bajo `rica-microservicios/`.

## Servicios y puertos

| Aplicación | Puerto | Responsabilidad |
|---|---:|---|
| Eureka Server | 8761 | Registro de servicios |
| API Gateway | 8000 | Enruta `/api/investigadores/**` y `/api/publicaciones/**` |
| investigadores-service | 8080 REST, 9090 gRPC | PostgreSQL, validación y outbox |
| publicaciones-service | 8081 REST | MongoDB, gRPC, Circuit Breaker y Saga |
| Kafka | 9092 | Eventos de dominio y coreografía |

## Requisitos

- JDK 25 y Maven 3.9.11 o superior
- Docker Desktop con Docker Compose

## Arranque

Desde `rica-microservicios/`, en terminales separadas:

1. `docker compose -f investigadores-service/docker-compose.yml up -d`
2. `docker compose -f publicaciones-service/docker-compose.yml up -d`
3. `mvn -pl eureka-server spring-boot:run`
4. `mvn -pl investigadores-service spring-boot:run`
5. `mvn -pl publicaciones-service spring-boot:run`
6. `mvn -pl gateway spring-boot:run`

Para comprobar compilación y pruebas: `mvn test`.

## Flujo de punta a punta

Registra un investigador con `POST http://localhost:8000/api/investigadores`:

```json
{"nombreCompleto":"Ana Torres","correoInstitucional":"ana.torres@uptc.edu.co","grupoInvestigacion":"GIT-UPTC"}
```

El outbox persiste `investigadores.registrados` junto al investigador; el poller lo publica a Kafka y publicaciones-service lo consume. Registra una publicación con `POST http://localhost:8000/api/publicaciones`:

```json
{"titulo":"DDD y microservicios","investigadorCorreo":"ana.torres@uptc.edu.co","anio":2025,"revista":"Revista RICA"}
```

La verificación del investigador ocurre por gRPC. Si el servicio no responde, el Circuit Breaker retorna 503; si el correo no existe, retorna 404. El servicio aplica el máximo de cinco publicaciones anuales.

Elimina con `DELETE http://localhost:8000/api/investigadores/{id}`. Responde 202; Kafka coordina que publicaciones-service marque publicaciones como huérfanas y confirme la baja, y luego investigadores-service borra el agregado.

## Decisiones y límites

- Eureka balancea llamadas HTTP del Gateway; el canal gRPC permanece fijo en `localhost:9090`, tal como delimita el taller.
- El consumidor de Saga publica su confirmación directamente a Kafka, como permite el enunciado; para producción convendría usar Outbox también para esa confirmación.
- El timeout de compensación que restaura `ACTIVO` se deja como reto opcional del taller.
- El repositorio de partida de Lección 5 no estaba disponible. Los servicios se reconstruyeron a partir del taller anterior y las rutas/respuestas siguen el contrato creado en esta copia.
