# Documentación de Desarrollo — `resi-complex-people-service`

**Responsable:** Alexandra Garzón  
**Rol:** Backend Developer & UX Specialist (Inmuebles, Personas y Mantenimiento)  
**Microservicio:** `resi-complex-people-service` (Puerto `8085` | Base de datos: `people_db`)

---

## Fase 1: Estructura Base Maven (Scaffolding)

- **[pom.xml](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/pom.xml):**
  - Parent: `org.springframework.boot:spring-boot-starter-parent:4.0.8` con **Java 25**.
  - `groupId`: `com.resicomplex` | `artifactId`: `people-service`.
  - Dependencias alineadas con la organización `resi-complex`: `spring-boot-starter-webmvc`, `spring-boot-starter-data-jpa`, `spring-boot-starter-flyway`, `spring-boot-starter-validation`, `flyway-mysql`, `mysql-connector-j`, `lombok`, `springdoc-openapi-starter-webmvc-ui:3.0.3` y starters de test.
- **[PeopleServiceApplication.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/PeopleServiceApplication.java):**
  - Clase principal `@SpringBootApplication` en el paquete base `com.resicomplex.people`.

---

## Fase 2: Configuración (`application.properties`) y Migración Flyway (`V1__init_people.sql`)

- **[V1__init_people.sql](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/resources/db/migration/V1__init_people.sql):**
  - Crea la tabla `persons` en `people_db` con llave primaria `id VARCHAR(36)`, datos personales (`first_name`, `last_name`, `document_type`, `document_number UNIQUE`, `email`, `phone`), clasificación (`person_type`: `RESIDENT` o `COMMERCIAL_OWNER_TENANT`), vínculo inmobiliario (`associated_unit_id VARCHAR(36)`), estado (`status DEFAULT 'ACTIVE'`) y timestamps de auditoría (`created_at`, `updated_at`, `deleted_at`).
  - Crea índices de consulta (`idx_persons_associated_unit_id`, `idx_persons_person_type`, `idx_persons_status`).
- **[application.properties](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/resources/application.properties):**
  - Puerto `8085` (`${SERVER_PORT:8085}`), conexión JDBC a `people_db`, validación estricta `spring.jpa.hibernate.ddl-auto=validate`, `spring.jpa.show-sql=true`, `spring.jpa.open-in-view=false` y Flyway activo.

---

## Fase 3: Capas de Dominio, Persistencia, DTOs, Servicio y Controlador

- **Modelo (`model/`):**
  - [DocumentType.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/model/DocumentType.java): Enum `CC`, `CE`, `NIT`, `PASSPORT`.
  - [PersonType.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/model/PersonType.java): Enum `RESIDENT`, `COMMERCIAL_OWNER_TENANT`.
  - [Person.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/model/Person.java): Entidad JPA mapeada a `persons`.
- **Repositorio (`repository/`):**
  - [PersonRepository.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/repository/PersonRepository.java): Extiende `JpaRepository<Person, String>` con `existsByDocumentNumber`, `findByPersonType` y `findByAssociatedUnitId`.
- **DTOs (`dto/`):**
  - [CreatePersonRequest.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/dto/CreatePersonRequest.java): Record de entrada con validaciones `@NotBlank`, `@NotNull`, `@Email` y `@Size`.
  - [PersonResponse.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/dto/PersonResponse.java): Record de salida para la API REST.
- **Servicio (`service/`):**
  - [IPersonService.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/service/IPersonService.java) y [PersonService.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/service/PersonService.java): Valida unicidad de `documentNumber` (`409 CONFLICT`), normaliza campos (`trim()`, email en minúsculas), asigna UUID y estado inicial `ACTIVE`, y maneja búsqueda por ID (`404 NOT_FOUND`), por tipo y por unidad.
- **Controlador (`controller/`):**
  - [PersonController.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/main/java/com/resicomplex/people/controller/PersonController.java): Expone `/api/v1/people` (y `/api/v1/persons` para compatibilidad con el API Gateway) con `POST`, `GET` (con filtros opcionales `personType` y `unitId`), `GET /{id}` y `GET /unit/{unitId}`.

---

## Fase 4: Pruebas Unitarias e Integración

- **[PersonServiceTest.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/test/java/com\resicomplex/people/service/PersonServiceTest.java):** 6 pruebas unitarias con JUnit 5 y Mockito.
- **[PeopleServiceApplicationTests.java](file:///c:/Users/Alexa_Var/Documents/resi-complex-people-service/src/test/java/com/resicomplex/people/PeopleServiceApplicationTests.java):** Prueba de integración que levanta el contexto de Spring Boot contra MySQL `people_db`, ejecuta la migración Flyway `V1__init_people.sql` y valida el esquema con Hibernate (`ddl-auto=validate`).
