<div align="justify">

# CentroPlus Connect

Plataforma de gestión de un centro académico y deportivo.  
Proyecto Intermodular · 1º DAM.

---

## Autor

| Nombre | GitHub |
|--------|--------|
| Iván Mesa Domínguez | @IvnMD |

---

## Tecnologías

| Componente | Tecnología |
|---|---|
| App de escritorio | JavaFX 21 |
| Backend API REST | Java 17 + Spring Boot |
| Base de datos | SQLite / MariaDB |
| Web | HTML + CSS + JavaScript |
| Despliegue | Docker |
| Build | Maven |

---

## Estructura del proyecto

```text
centroplus-connect/
├── backend-api/        # API REST Spring Boot
├── src/                # App escritorio JavaFX
├── database/
│   ├── schema.sql
│   └── diagrama-er.png
├── docker-compose.yml
└── README.md
```

---

## Instalación y ejecución

### Base de datos
```bash
cd database
sqlite3 centroplus.db < schema.sql
```

### Backend API REST
```bash
cd backend-api
mvn spring-boot:run
```
La API arranca en `http://localhost:8080`

### Tests
```bash
mvn clean test
```

---

## Endpoints API

| Método | Ruta | Descripción |
|---|---|---|
| GET | /api/actividades | Listar actividades |
| POST | /api/actividades | Crear actividad |
| GET | /api/actividades/{id} | Obtener actividad |
| PUT | /api/actividades/{id} | Actualizar actividad |
| DELETE | /api/actividades/{id} | Eliminar actividad |
| GET | /api/usuarios | Listar usuarios |
| POST | /api/usuarios | Crear usuario |
| GET | /api/usuarios/{id} | Obtener usuario |
| PUT | /api/usuarios/{id} | Actualizar usuario |
| DELETE | /api/usuarios/{id} | Eliminar usuario |
| GET | /api/inscripciones | Listar inscripciones |
| POST | /api/inscripciones | Crear inscripción |
| GET | /api/inscripciones/{id} | Obtener inscripción |
| DELETE | /api/inscripciones/{id} | Eliminar inscripción |

---

## Ramas del proyecto

```text
main            ← versiones estables
develop         ← integración continua
feature/backend
feature-mobile
feature-web
feature-database
feature-docker
feature-docs
```

---

## Arquitectura

```text
App JavaFX → API REST → Servicios → Repositorios → SQLite/MariaDB
Web HTML   → API REST
```

## Nota

La app móvil no fue implementada por limitaciones de tiempo.  
El backend REST está preparado para ser consumido desde cualquier cliente.

</div>
