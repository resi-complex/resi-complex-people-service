# resi-complex-people-service

Microservicio de **Directorio de Personas** para el ecosistema `resi-complex` (Sistema de Gestión Integral para Conjuntos Residenciales Mixtos). Gestiona el registro de residentes y propietarios/arrendatarios de establecimientos comerciales cumpliendo con lineamientos de protección de datos (Ley 1581).

## Stack Tecnológico
- **Java:** 25
- **Framework:** Spring Boot 4.0.8
- **Base de Datos:** MySQL (`people_db`)
- **Migraciones:** Flyway (`V1__init_people.sql`)
- **Documentación API:** OpenAPI 3 / Swagger UI (`springdoc-openapi 3.0.3`)

## Configuración Local
- **Puerto por defecto:** `8085`
- **Swagger UI:** `http://localhost:8085/swagger-ui/index.html`
- **Variable de entorno requerida:** `DB_PASSWORD` (contraseña del usuario MySQL `resi_dev`).

## Endpoints Principales (`/api/v1/people`)
- `POST /api/v1/people` — Registra una nueva persona (`RESIDENT` o `COMMERCIAL_OWNER_TENANT`).
- `GET /api/v1/people` — Lista todas las personas (filtros opcionales `?personType=...` o `?unitId=...`).
- `GET /api/v1/people/{id}` — Obtiene el detalle de una persona por su UUID.
- `GET /api/v1/people/unit/{unitId}` — Lista las personas vinculadas a una unidad específica.
