# Práctica 3: Microservicios MongoDB y PostgreSQL

## 📋 Descripción General

Este proyecto implementa dos microservicios independientes que trabajan conjuntamente para gestionar datos de películas y actores en dos bases de datos distintas:

- **MongoChamador**: Microservicio que interactúa con MongoDB, realiza llamadas al otro microservicio y ejecuta una secuencia de operaciones.
- **PelisPostgres**: API REST que expone endpoints CRUD para gestionar películas y actores en PostgreSQL.

---

## 🏗️ Arquitectura del Proyecto

### Estructura de Carpetas

```
Ad_Tarea3-main/
├── MongoChamador/          # Microservicio de MongoDB
│   ├── pom.xml
│   └── src/main/java/org/example/
│       ├── Main.java                    # Punto de entrada Spring Boot
│       ├── Secuencia.java               # Orquestador del flujo
│       ├── config/Config.java           # Configuración (RestTemplate)
│       ├── model/
│       │   ├── Pelicula.java
│       │   └── Actor.java
│       ├── repository/PeliculaRepository.java
│       └── service/
│           ├── Conexion.java            # Comunica con PelisPostgres
│           └── PeliculaService.java     # Gestiona MongoDB
│
├── PelisPostgres/          # Microservicio de PostgreSQL
│   ├── pom.xml
│   └── src/main/java/org/example/
│       ├── Main.java                    # Punto de entrada Spring Boot
│       ├── config/OpenApiConfig.java    # Swagger/OpenAPI
│       ├── controller/
│       │   ├── RestPeliculas.java       # Endpoints de películas
│       │   └── RestActores.java         # Endpoints de actores
│       ├── model/
│       │   ├── Pelicula.java
│       │   └── Actor.java
│       ├── repository/
│       │   ├── PeliculaRepository.java
│       │   └── ActorRepository.java
│       └── service/
│           ├── PeliculaService.java
│           └── ActorService.java
│
└── peliculas.json          # Archivo exportado con datos de MongoDB
```

---

## 🗄️ Modelos de Datos

### PostgreSQL

#### Tabla: `peliculas`
```sql
CREATE TABLE peliculas (
    idPelicula SERIAL PRIMARY KEY,
    titulo VARCHAR(150),
    xenero VARCHAR(50),
    ano INT
);
```

#### Tabla: `actores`
```sql
CREATE TABLE actores (
    idActor SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    apelidos VARCHAR(100),
    nacionalidade VARCHAR(100),
    id_pelicula INT REFERENCES peliculas(idPelicula)
);
```

### MongoDB

Colección: `peliculas`
```json
{
    "_id": "ObjectId",
    "idPelicula": "String",
    "titulo": "String",
    "xenero": "String",
    "ano": "Integer",
    "actores": [
        {
            "idActor": "String",
            "nome": "String",
            "apelidos": "String",
            "nacionalidade": "String",
            "idPelicula": "Long"
        }
    ]
}
```

---

## 🚀 Microservicios

### 1. **PelisPostgres** (Puerto: 8080)

#### Propósito
Actúa como un servidor REST que expone endpoints CRUD para películas y actores almacenados en PostgreSQL.

#### Dependencias Principales
- Spring Data JPA (para acceso a datos)
- PostgreSQL Driver
- SpringDoc OpenAPI (Swagger UI)

#### Endpoints Disponibles

##### Películas - `/postgres/peliculas`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/` | Obtiene todas las películas |
| GET | `/{id}` | Obtiene una película por ID |
| GET | `/titulo/{titulo}` | Obtiene películas por título |
| POST | `/` | Crea una nueva película |
| PUT | `/{id}` | Actualiza una película |
| DELETE | `/{id}` | Elimina una película |

##### Actores - `/postgres/actores`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/` | Obtiene todos los actores |
| GET | `/{id}` | Obtiene un actor por ID |
| POST | `/` | Crea un nuevo actor |
| PUT | `/{id}` | Actualiza un actor |
| DELETE | `/{id}` | Elimina un actor |

#### Documentación API
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`

#### Configuración (`application.properties`)
```properties
app.version=1.0.0
spring.application.name=PelisPostgres

# Conexión a PostgreSQL
spring.datasource.url=jdbc:postgresql://10.0.9.100:5432/probas
spring.datasource.username=postgres
spring.datasource.password=admin
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
server.port=8080
```

---

### 2. **MongoChamador** (Puerto: 8095)

#### Propósito
Orquesta una secuencia de operaciones que:
1. Consulta datos en PostgreSQL mediante llamadas HTTP
2. Almacena datos en MongoDB
3. Exporta datos de MongoDB a JSON

#### Flujo de Ejecución (Método `executar()` en `Secuencia.java`)

```
1. Obtener película de PostgreSQL por ID (ID=9)
   ↓
2. Obtener películas de PostgreSQL por título ("Top Gun")
   ↓
3. Guardar película por ID en MongoDB
   ↓
4. Guardar película por título en MongoDB
   ↓
5. Exportar todas las películas de MongoDB a "peliculas.json"
```

#### Componentes Clave

##### **Secuencia.java**
- Coordinador principal del flujo
- Inyecta `Conexion` y `PeliculaService`
- Se ejecuta automáticamente con `@PostConstruct` tras iniciar Spring

##### **Conexion.java**
- Realiza peticiones HTTP al microservicio PelisPostgres
- Usa `RestTemplate` para comunicación REST
- Métodos:
  - `getAllPeliculas()`: GET todas las películas
  - `getPeliculaByTitulo(String)`: GET películas por título
  - `getAPeliculaById(Long)`: GET película por ID
  - `createPelicula(Pelicula)`: POST crear película

##### **PeliculaService.java** (MongoDB)
- Gestiona operaciones CRUD en MongoDB
- Métodos principales:
  - `crearPelicula(Pelicula)`: Guarda una película en MongoDB
  - `findAll()`: Obtiene todas las películas
  - `importarDesdeJSON(String)`: Lee películas desde JSON
  - `exportarAJSON()`: Exporta películas a `peliculas.json`
  - `obtenerPorId(String)`: Busca por ID
  - `obtenerPorTitulo(String)`: Busca por título

#### Dependencias Principales
- Spring Data MongoDB (para acceso a MongoDB)
- Spring Web (para RestTemplate)
- Gson y Jackson (para serialización JSON)
- SpringDoc OpenAPI

#### Configuración (`application.properties`)
```properties
app.version=1.0.0
spring.application.name=MongoChamador

# Conexión a MongoDB
spring.data.mongodb.uri=mongodb://10.0.9.100:27017/probas

# Swagger/OpenAPI
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/swagger-ui/index.html
server.port=8095
```

---

## 🔄 Flujo de Comunicación Entre Microservicios

```
MongoChamador (8095)
    ↓
    [RestTemplate / HTTP]
    ↓
PelisPostgres (8080)
    ↓
    [JDBC / JPA]
    ↓
PostgreSQL (5432)
    ↑
    [MongoDB Driver]
    ↑
MongoDB (27017)
```

### Pasos Detallados:

1. **Inicio de MongoChamador**
   - Spring Boot inicia `Main.java`
   - Inyecta `Secuencia` y llama al método `executar()` con `@PostConstruct`

2. **Conexión a PostgreSQL** (vía REST)
   - `Conexion.getAPeliculaById(9)` → GET a `http://localhost:8080/postgres/peliculas/9`
   - `Conexion.getPeliculaByTitulo("Top Gun")` → GET a `http://localhost:8080/postgres/peliculas/titulo/Top Gun`

3. **Almacenamiento en MongoDB**
   - `PeliculaService.crearPelicula(pelicula)` → Guarda en colección `peliculas` de MongoDB

4. **Exportación a JSON**
   - `PeliculaService.exportarAJSON()` → Crea/sobrescribe `peliculas.json` con todas las películas

---

## 🛠️ Tecnologías Utilizadas

### Framework y Librerías

| Dependencia | Propósito |
|-------------|-----------|
| Spring Boot 3.4.2 | Framework principal |
| Spring Data JPA | ORM para PostgreSQL |
| Spring Data MongoDB | Driver para MongoDB |
| PostgreSQL Driver 42.5.4 | Conector JDBC |
| Gson 2.10.1 | Serialización JSON |
| Jackson | Serialización JSON |
| SpringDoc OpenAPI 2.5.0 | Documentación API (Swagger) |
| Jakarta Persistence | Anotaciones JPA |

### Bases de Datos

| BD | Puerto | URI |
|-------|--------|-----|
| PostgreSQL | 5432 | `jdbc:postgresql://10.0.9.100:5432/probas` |
| MongoDB | 27017 | `mongodb://10.0.9.100:27017/probas` |

---

## 📦 Instalación y Ejecución

### Requisitos Previos
- Java 17+
- Maven 3.6+
- PostgreSQL 12+
- MongoDB 4.0+

### Pasos de Configuración

#### 1. **Crear Base de Datos en PostgreSQL**
```sql
CREATE DATABASE probas;

-- Conectarse a probas
\c probas

CREATE TABLE peliculas (
    idPelicula SERIAL PRIMARY KEY,
    titulo VARCHAR(150),
    xenero VARCHAR(50),
    ano INT
);

CREATE TABLE actores (
    idActor SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    apelidos VARCHAR(100),
    nacionalidade VARCHAR(100),
    id_pelicula INT REFERENCES peliculas(idPelicula)
);
```

#### 2. **Crear Base de Datos en MongoDB**
```javascript
use probas;
db.createCollection("peliculas");
```

#### 3. **Compilar PelisPostgres**
```bash
cd PelisPostgres
mvn clean package
mvn spring-boot:run
```
- Se ejecutará en `http://localhost:8080`

#### 4. **Compilar MongoChamador**
```bash
cd MongoChamador
mvn clean package
mvn spring-boot:run
```
- Se ejecutará en `http://localhost:8095`
- Automáticamente ejecutará la secuencia

---

## 🧪 Ejemplo de Uso

### 1. Insertar Datos en PostgreSQL (mediante POST)
```bash
curl -X POST http://localhost:8080/postgres/peliculas \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Top Gun",
    "xenero": "Acción",
    "ano": 1986
  }'
```

### 2. Consultar Película por ID
```bash
curl http://localhost:8080/postgres/peliculas/1
```

### 3. Consultar Película por Título
```bash
curl http://localhost:8080/postgres/peliculas/titulo/Top%20Gun
```

### 4. Ver Documentación Swagger
- PelisPostgres: `http://localhost:8080/swagger-ui/index.html`
- MongoChamador: `http://localhost:8095/swagger-ui/index.html`

### 5. Archivo JSON Generado
Tras ejecutar MongoChamador, se genera `peliculas.json`:
```json
[
  {
    "idPelicula": "507f1f77bcf86cd799439011",
    "titulo": "Top Gun",
    "xenero": "Acción",
    "ano": 1986,
    "actores": [
      {
        "idActor": "1",
        "nome": "Tom",
        "apelidos": "Cruise",
        "nacionalidade": "USA",
        "idPelicula": 1
      }
    ]
  }
]
```

---

## 📝 Anotaciones Importantes

### Pattern REST en PelisPostgres
- Los repositorios extienden `JpaRepository` para operaciones automáticas
- Los servicios encapsulan la lógica de negocio
- Los controladores REST exponen los endpoints
- Se usa `ResponseEntity` para manejo de respuestas HTTP

### Pattern en MongoChamador
- `Secuencia` actúa como un orquestador
- `Conexion` maneja la comunicación HTTP con PostgreSQL
- `PeliculaService` maneja la persistencia en MongoDB
- `@PostConstruct` asegura que `executar()` se corra después de la inicialización

### Serialización JSON
- **Gson**: Se usa en `importarDesdeJSON()` para convertir JSON a objetos
- **Jackson**: Se usa en `exportarAJSON()` para convertir objetos a JSON con formato

### Relaciones entre Tablas
- En PostgreSQL: Relación 1-a-N (Pelicula → Actores) mediante ForeignKey
- En MongoDB: Los actores se incrustan dentro del documento de película (desnormalización)

---

## 🔍 Resolución de Problemas

### Las conexiones HTTP fallan
**Causa**: El microservicio PelisPostgres no está ejecutándose
**Solución**: Verificar que `http://localhost:8080` está disponible

### MongoDB no conecta
**Causa**: URI incorrecta o MongoDB no está disponible
**Solución**: Verificar que MongoDB corre en `10.0.9.100:27017`

### PostgreSQL no conecta
**Causa**: Credenciales incorrectas o BD no existe
**Solución**: Verificar usuario/contraseña y que existe base de datos `probas`

### JSON no se genera
**Causa**: Permisos insuficientes o ruta incorrecta
**Solución**: Verificar permisos de escritura en el directorio del proyecto

---

## 📚 Referencias y Recursos

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [MongoDB Documentation](https://docs.mongodb.com/)
- [Maven Central Repository](https://mvnrepository.com/)

---

## 👨‍💻 Autor

Práctica 3 - Desarrollo de Aplicaciones (Microservicios con MongoDB y PostgreSQL)

---

## 📄 Licencia

Este proyecto es parte de una práctica educativa.

---

## 📞 Notas Finales

Este proyecto demuestra:
- ✅ Comunicación entre microservicios mediante REST
- ✅ Integración de múltiples bases de datos
- ✅ Patrón arquitectónico de servicios
- ✅ Orquestación de flujos complejos
- ✅ Serialización/deserialización de datos
- ✅ Documentación API con Swagger
- ✅ Inyección de dependencias con Spring
