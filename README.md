# SCUNSIS Backend

Sistema backend para la gestion de constancias academicas en eventos universitarios (congresos, jornadas, simposios, conferencias, etc.).

## Stack tecnologico

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- PostgreSQL
- Apache POI (lectura de Excel)
- OpenPDF (generacion de PDF)
- Flyway (migraciones de base de datos)
- SpringDoc OpenAPI (Swagger UI)
- Docker (contenedores)
- Docker-compose (orquestacion)

## Migraciones de base de datos (Flyway)

El esquema de base de datos se gestiona con **Flyway**. Las migraciones estan en `src/main/resources/db/migration/`.

- **Nuevos cambios**: crear un archivo `V{version}__{descripcion}.sql` en ese directorio
- **Baseline**: si ya tienes una base de datos existente, Flyway la baselines automaticamente gracias a `spring.flyway.baseline-on-migrate=true`
- **Seed data**: los datos iniciales (usuarios, emisores, receptores, eventos, actividades) se insertan via `DataInitializer.java` (un `CommandLineRunner`) para que las contraseñas se tomen de variables de entorno. Si necesitas datos fijos, puedes crear una migracion V2.

> [!IMPORTANT]
> `spring.jpa.hibernate.ddl-auto=none` — Flyway es el unico responsable del esquema. No uses `ddl-auto=update`.

## Flujo de trabajo (GitFlow)

> [!IMPORTANT]
> No hacer push directo a `main` ni `dev`. Todo cambio debe realizarse mediante **Pull Request (PR)**. Cada PR debe ser revisada y aprobada por al menos un **reviewer** antes de hacer merge. Solo **EleazarDevFS** está autorizado para aprobar y mergear los cambios.

Este proyecto sigue el modelo **GitFlow** para la gestion de ramas.

### Ramas principales

| Rama | Proposito |
|------|-----------|
| `main` | Produccion. Solo recibe merges de `release` o `hotfix`. |
| `dev` | Integracion. Rama base para `feature` y fuente para `release`. |

### Ramas de soporte

| Rama | Nace de | Se mergea a | Convencion de nombre |
|------|---------|-------------|----------------------|
| **Feature** | `dev` | `dev` | `feat/<nombre-descripcion>` |
| **Fix** | `dev` | `dev` | `fix/<nombre-descripcion>` |
| **Hotfix** | `main` | `main` y `develop` | `hotfix/<nombre-descripcion>` |

### Flujo basico

```bash
# 1. Crear una rama feature desde develop
git checkout dev
git checkout -b feat/nombre-de-la-feature
```


## Requisitos

- Docker + Docker Compose

## Inicio rapido (con Docker)

```bash
# Clonar el proyecto y entrar
cd scunsis-backend

# Iniciar todos los servicios
docker compose up -d
```

Esto levanta tres contenedores:

| Servicio | Puerto host | Descripcion |
|----------|-------------|-------------|
| **db** | `5433` | PostgreSQL 16 |
| **backend** | `8082` | API Spring Boot |
| **frontend** | `5173` | Nginx sirviendo Vue + proxy a backend |

### Accesos

| Que | URL |
|-----|-----|
| Frontend | http://localhost:5173 |
| API Backend | http://localhost:8082 |
| Swagger UI | http://localhost:8082/swagger-ui.html |
| Base de datos (host) | `localhost:5433` usuario: `usuario` pass: `password` |

### Credenciales iniciales

| Usuario | Rol | Password (default) |
|---------|-----|--------------------|
| `admin` | ADMIN | `password` |
| `capturista` | CAPTURISTA | `password` |

> [!NOTE]
> Las contraseñas se configuran via variables de entorno en `.env`.

> [!TIP]
> Todo usuario nuevo (incluyendo admin y capturista) al iniciar sesion por primera vez debera cambiar su contraseña. El campo `mustChangePassword` se inicializa en `true` y la interfaz redirige automaticamente al formulario de cambio de contraseña.

### Comandos utiles

```bash
# Ver logs de todos los servicios
docker compose logs -f

# Ver logs de un servicio especifico
docker compose logs -f backend
docker compose logs -f frontend
docker compose logs -f db

# Detener servicios (sin borrar datos)
docker compose down

# Detener y borrar volumenes (BD y PDFs)
docker compose down -v

# Reconstruir imagenes desde cero
docker compose build --no-cache

# Ver estado de los servicios
docker compose ps
```

### Persistencia de datos

Docker Compose define dos volumenes:

- **postgres_data**: datos de la base de datos (sobrevive a `docker compose down`)
- **constancias_data**: PDFs generados, montado en `/app/constancias` del backend

> [!IMPORTANT]
> Para acceder a los PDFs desde el host, cambia el volumen por un bind mount:

```yaml
volumes:
  - ./pdfs:/app/constancias
```

## Arquitectura con Docker

```
Host:5433              Host:8082              Host:5173
  │                       │                       │
  v                       v                       v
┌──────────┐          ┌──────────┐          ┌──────────┐
│PostgreSQL│◄────────►│  Spring  │◄────────►│  Nginx   │
│   :5432  │  red int │  Boot    │  red int │  :80     │
└──────────┘          │  :8082   │          │ /api/ →  │
                      └──────────┘          │ backend  │
                                             └──────────┘
```

El frontend se sirve como SPA con Nginx. Las llamadas a `/api/*` se redirigen automaticamente al backend, evitando problemas de CORS.

## Sin Docker (desarrollo local)

```bash
# Requisitos: Java 21, PostgreSQL corriendo en localhost:5432

# Compilar
./gradlew compileJava

# Ejecutar pruebas
./gradlew test

# Iniciar servidor
./gradlew bootRun
```

## Variables de entorno

Configuracion via `.env` (usado por Docker Compose automaticamente):

| Variable | Descripcion | Default |
|----------|-------------|---------|
| `URL_DB` | URL de conexion a BD | `jdbc:postgresql://db:5432/scunsis` |
| `USER_DB` | Usuario BD | `postgres` |
| `PASS_DB` | Password BD | `postgres` |
| `SERVER_PORT` | Puerto del backend | `8082` |
| `JWT_SECRET` | Secreto para firmar tokens JWT | —(Generar con: `openssl rand -base64 64`) |
| `JWT_EXPIRATION` | Duracion del token en segundos | `864000` |
| `PDF_PATH_SAVE` | Directorio para PDFs generados | `./constancias` |
| `MAX_FILE` | Tamano maximo de subida | `10MB` |
| `ADMIN_PASSWORD` | Password del usuario admin | `admin` |
| `CAPTURISTA_PASSWORD` | Password del usuario capturista | `capturista` |

> [!CAUTION]
> En produccion cambia `ADMIN_PASSWORD`, `CAPTURISTA_PASSWORD` y `JWT_SECRET`.

## Modelo de datos

```
Evento (event) ──1:N──> Actividad (activity) ──1:N──> Constancia (proof)
                                                         │
Emisor (sender) ──1:N────────────────────────────────────┘
                                                         │
Receptor (receiver) ──1:N───────────────────────────────┘
```

### Roles de constancia

| Rol | Codigo | Descripcion |
|-----|--------|-------------|
| PONENTE | PON | Persona que impartio una ponencia o conferencia |
| PARTICIPANTE | PAR | Asistente a la actividad |
| ORGANIZADOR | ORG | Miembro del equipo organizador |
| RECONOCIMIENTO | REC | Reconocimiento especial |

### Formato de folio

Los folios se generan automaticamente con el formato: `CODIGO-YYYY-NNNN`

Ejemplo: `PON-2025-0001`, `PAR-2025-0042`

## Carga masiva desde Excel

### Endpoint

```
POST /api/v1/proof/upload
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

| Nombre | PrimerApellido | SegundoApellido | Email | Telefono | GradoAcademico | Rol |
|--------|---------------|-----------------|-------|----------|----------------|-----|
| Juan | Perez | Garcia | juan@mail.com | 5551234567 | Lic. | PARTICIPANTE |
| Maria | Lopez | | maria@mail.com | 5559876543 | Dra. | PONENTE |

> [!WARNING]
> Las columnas **Nombre** y **PrimerApellido** son obligatorias. Sin ellas la fila se omite silenciosamente.

- **Email**: si ya existe un receptor con ese correo, se reutiliza en lugar de crear uno nuevo.
- **Rol**: PONENTE, PARTICIPANTE, ORGANIZADOR o RECONOCIMIENTO.

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

### Autenticacion

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/api/login` | Iniciar sesion |
| POST | `/api/change-password` | Cambiar contraseña |

### Eventos

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/api/v1/event` | Crear evento |
| GET | `/api/v1/event` | Listar eventos |
| GET | `/api/v1/event/{id}` | Obtener evento por ID |
| DELETE | `/api/v1/event/{id}` | Eliminar evento |

### Actividades

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/api/v1/activity` | Crear actividad |
| GET | `/api/v1/activity` | Listar actividades |
| GET | `/api/v1/activity/{id}` | Obtener actividad por ID |
| DELETE | `/api/v1/activity/{id}` | Eliminar actividad |

### Constancias

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/api/v1/proof` | Crear constancia individual |
| GET | `/api/v1/proof` | Listar constancias |
| GET | `/api/v1/proof/{folio}` | Obtener constancia por folio |
| GET | `/api/v1/proof/{folio}/pdf` | Descargar constancia en PDF |
| DELETE | `/api/v1/proof/{folio}` | Eliminar constancia |
| POST | `/api/v1/proof/upload` | Carga masiva desde Excel |

### Emisores

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/api/v1/sender` | Crear emisor |
| GET | `/api/v1/sender` | Listar emisores |
| GET | `/api/v1/sender/{id}` | Obtener emisor por ID |
| DELETE | `/api/v1/sender/{id}` | Eliminar emisor |

### Receptores

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/api/v1/receiver` | Crear receptor |
| GET | `/api/v1/receiver` | Listar receptores |
| GET | `/api/v1/receiver/{id}` | Obtener receptor por ID |
| DELETE | `/api/v1/receiver/{id}` | Eliminar receptor |

### Archivos

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/api/file` | Subir Excel y obtener datos parseados |
| POST | `/api/upload-excel` | Subir Excel y extraer folios |
