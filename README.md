# SCUNSIS Backend

Sistema backend para la gestion de constancias academicas en eventos universitarios (congresos, jornadas, simposios, conferencias, etc.).

## Stack tecnologico

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- PostgreSQL
- Apache POI (lectura de Excel)
- OpenPDF (generacion de PDF)
- SpringDoc OpenAPI (Swagger UI)

## Modelo de datos

```
Evento (event) ──1:N──> Actividad (activity) ──1:N──> Constancia (proof)
                                                         │
Emisor (sender) ──1:N─────────────────────────────────────┘
                                                         │
Receptor (receiver) ──1:N────────────────────────────────┘
```

- **Evento**: Congreso, jornada, simposio, conferencia, foro. Puede ser presencial (FISICO) o virtual (VIRTUAL).
- **Actividad**: Subcomponente de un evento (ponencia, taller, panel, conferencia).
- **Constancia**: Certificado emitido a una persona por su participacion en una actividad. Cada constancia tiene un folio unico.
- **Emisor**: Entidad que emite la constancia (facultad, coordinacion, departamento).
- **Receptor**: Persona que recibe la constancia.

### Roles de constancia

Cada constancia se asigna con un rol que define la participacion de la persona:

| Rol | Codigo | Descripcion |
|-----|--------|-------------|
| PONENTE | PON | Persona que impartio una ponencia o conferencia |
| PARTICIPANTE | PAR | Asistente a la actividad |
| ORGANIZADOR | ORG | Miembro del equipo organizador |
| RECONOCIMIENTO | REC | Reconocimiento especial |

### Formato de folio

Los folios se generan automaticamente con el formato: `CODIGO-YYYY-NNNN`

Ejemplo: `PON-2025-0001`, `PAR-2025-0042`

La secuencia es independiente por cada combinacion de rol + año.

## Carga masiva desde Excel

### Endpoint

```
POST /scunsis/api/v1/proof/upload
Content-Type: multipart/form-data
```

### Parametros

| Parametro | Tipo | Requerido | Descripcion |
|-----------|------|-----------|-------------|
| file | File | SI | Archivo .xlsx con los datos de los participantes |
| eventId | Long | SI | ID del evento |
| activityId | Long | SI | ID de la actividad |
| senderId | Long | SI | ID del emisor |
| role | String | NO | Rol por defecto si el Excel no incluye la columna ROL |

### Estructura del Excel

La primera fila debe contener los encabezados. El orden de las columnas es indistinto.

| Nombre | PrimerApellido | SegundoApellido | Email | Telefono | GradoAcademico | Rol |
|--------|---------------|-----------------|-------|----------|----------------|-----|
| Juan | Perez | Garcia | juan@mail.com | 5551234567 | Lic. | PARTICIPANTE |
| Maria | Lopez | | maria@mail.com | 5559876543 | Dra. | PONENTE |

- **Nombre** y **PrimerApellido**: obligatorios. Sin ellos la fila se omite.
- **Email**: si ya existe un receptor con ese correo, se reutiliza en lugar de crear uno nuevo.
- **Rol**: PONENTE, PARTICIPANTE, ORGANIZADOR o RECONOCIMIENTO. Si no se incluye la columna, se usa el parametro `role` del endpoint.

### Respuesta

```json
{
  "totalRows": 50,
  "successCount": 50,
  "errorCount": 0,
  "generatedFolios": ["PAR-2025-0001", "PAR-2025-0002", ...],
  "errors": []
}
```

## API Endpoints

### Eventos

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/scunsis/api/v1/event` | Crear evento |
| GET | `/scunsis/api/v1/event` | Listar eventos |
| GET | `/scunsis/api/v1/event/{id}` | Obtener evento por ID |
| DELETE | `/scunsis/api/v1/event/{id}` | Eliminar evento |

### Actividades

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/scunsis/api/v1/activity` | Crear actividad |
| GET | `/scunsis/api/v1/activity` | Listar actividades |
| GET | `/scunsis/api/v1/activity/{id}` | Obtener actividad por ID |
| DELETE | `/scunsis/api/v1/activity/{id}` | Eliminar actividad |

### Constancias

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/scunsis/api/v1/proof` | Crear constancia individual |
| GET | `/scunsis/api/v1/proof` | Listar constancias |
| GET | `/scunsis/api/v1/proof/{folio}` | Obtener constancia por folio |
| GET | `/scunsis/api/v1/proof/by-activity/{activityId}` | Listar constancias de una actividad |
| GET | `/scunsis/api/v1/proof/{folio}/pdf` | Descargar constancia en PDF |
| DELETE | `/scunsis/api/v1/proof/{folio}` | Eliminar constancia |
| POST | `/scunsis/api/v1/proof/upload` | Carga masiva desde Excel |

### Emisores

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/scunsis/api/v1/sender` | Crear emisor |
| GET | `/scunsis/api/v1/sender` | Listar emisores |
| GET | `/scunsis/api/v1/sender/{id}` | Obtener emisor por ID |
| DELETE | `/scunsis/api/v1/sender/{id}` | Eliminar emisor |

### Receptores

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/scunsis/api/v1/receiver` | Crear receptor |
| GET | `/scunsis/api/v1/receiver` | Listar receptores |
| GET | `/scunsis/api/v1/receiver/{id}` | Obtener receptor por ID |
| DELETE | `/scunsis/api/v1/receiver/{id}` | Eliminar receptor |

## Documentacion Swagger

Una vez iniciado el servidor, la documentacion interactiva esta disponible en:

```
http://localhost:8082/swagger-ui.html
```

## Base de datos

### Configuracion

Las credenciales por defecto estan en `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/scunsis
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

Con `ddl-auto=create` las tablas se crean automaticamente al iniciar la aplicacion.

### Datos de prueba

Ejecutar el script `populate_data.sql` contra la base de datos para cargar datos de ejemplo.

## Construccion y ejecucion

```bash
# Compilar
./gradlew compileJava

# Ejecutar pruebas
./gradlew test

# Iniciar servidor
./gradlew bootRun
```
