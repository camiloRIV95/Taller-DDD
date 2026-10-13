# Taller DDD — RICA API

## Auditoría del Lenguaje Ubicuo

Los nombres `nombreCompleto`, `correoInstitucional`, `grupoInvestigacion` e `investigadorCorreo` describen conceptos del dominio y se conservan. `investigadorCorreo` expresa que la publicación identifica a su investigador por el correo institucional; nombres como `refId` esconderían esa relación detrás de una referencia técnica genérica.

## Límite del Agregado Investigador

`Investigador` es la raíz del agregado. Dentro del límite están `id`, `nombreCompleto`, `correoInstitucional` (Value Object) y `grupoInvestigacion`. `Publicacion` permanece fuera: un investigador puede acumular muchas publicaciones, y cargarlas como colección ampliaría transacciones, acoplaría ambos modelos y permitiría conflictos al actualizar listas concurrentemente. La publicación conserva solo `investigadorCorreo`, una referencia por identidad de dominio, y el agregado puede evolucionar por separado.

## Contrato HTTP de esta copia

Esta copia no incluye el código legado de Tutorial 6, así que no es posible certificar compatibilidad con sus rutas o respuestas. Se define una API inicial explícita: `POST /api/investigadores`, `GET /api/investigadores`, `GET /api/investigadores/{id}`, `POST /api/publicaciones` y `GET /api/publicaciones?investigadorCorreo=...`.
