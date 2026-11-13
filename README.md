# Sistema de Gestión de Albergue de Mascotas

Sistema de escritorio desarrollado en **Java** que permite administrar y organizar las principales actividades de un albergue de mascotas mediante una interfaz gráfica.

El proyecto facilita la gestión de mascotas, adoptantes, solicitudes de adopción, historiales clínicos y donaciones, utilizando programación orientada a objetos y almacenamiento de información en archivos JSON.

---

## Descripción del proyecto

El **Sistema de Gestión de Albergue de Mascotas** tiene como objetivo facilitar el trabajo administrativo de un albergue, centralizando la información y permitiendo llevar un mejor control de los animales, sus procesos de adopción, atenciones médicas y recursos recibidos.

Mediante una aplicación gráfica, el personal puede registrar, consultar, actualizar y eliminar información, así como realizar el seguimiento de las diferentes operaciones del albergue.

### Objetivos

- Organizar la información de las mascotas registradas.
- Facilitar el proceso de adopción.
- Mantener un seguimiento de los historiales clínicos.
- Administrar las donaciones recibidas.
- Conservar la información mediante persistencia de datos.
- Mejorar la organización y el control de las actividades del albergue.

---

## Funcionalidades principales

### Gestión de mascotas

- Registrar nuevas mascotas.
- Consultar mascotas mediante su identificador.
- Buscar registros por ID, nombre o raza.
- Editar información de las mascotas.
- Eliminar registros cuando cumplen las condiciones establecidas.
- Consultar información detallada.
- Gestionar estados: `Refugiado`, `Tratamiento` y `Adoptado`.

### Gestión de adopciones

- Registrar adoptantes.
- Identificar adoptantes mediante su DNI.
- Crear solicitudes de adopción.
- Consultar solicitudes registradas.
- Buscar solicitudes.
- Aprobar o rechazar solicitudes pendientes.
- Actualizar automáticamente el estado de la mascota cuando se aprueba una solicitud.

Los estados de las solicitudes son:

- `Pendiente`
- `Aprobada`
- `Rechazada`

### Gestión de historiales clínicos

- Registrar atenciones médicas.
- Asociar cada atención con una mascota.
- Consultar historiales clínicos.
- Editar registros médicos.
- Eliminar registros clínicos.
- Registrar fechas, procedimientos, diagnósticos, tratamientos y veterinarios.

### Gestión de donaciones

- Registrar donaciones en efectivo.
- Registrar donaciones de alimentos.
- Consultar los registros de donaciones.
- Buscar donaciones.
- Visualizar detalles.
- Eliminar registros.
- Calcular el total de dinero recibido.
- Calcular la cantidad total de alimentos recibidos en kilogramos.

### Inicio de sesión

- Acceso mediante usuario y contraseña.
- Validación de credenciales.
- Límite de tres intentos para ingresar correctamente.
- Cierre de la ventana de acceso cuando se agotan los intentos.

---

## Tecnologías utilizadas

| Tecnología | Utilización |
|---|---|
| Java | Lenguaje principal de desarrollo |
| JDK | Entorno de desarrollo y ejecución |
| Java Swing | Desarrollo de interfaces gráficas |
| NetBeans IDE | Entorno de desarrollo |
| Gson | Serialización y deserialización de objetos |
| JSON | Almacenamiento persistente de información |
| Java Collections Framework | Administración de colecciones |
| ArrayList y List | Almacenamiento de registros en memoria |
| Java Streams | Búsqueda y procesamiento de información |
| Java Time API | Gestión de fechas mediante `LocalDate` |

---

## Estructura del proyecto

El proyecto está organizado mediante paquetes para separar las responsabilidades de cada componente.

```text
albergue-mascota/
│
├── src/
│   └── com/
│       └── albergue/
│           │
│           ├── App.java
│           │
│           ├── modelo/
│           │   ├── Adoptante.java
│           │   ├── Autenticable.java
│           │   ├── Comida.java
│           │   ├── Donacion.java
│           │   ├── Efectivo.java
│           │   ├── HistorialClinico.java
│           │   ├── Mascota.java
│           │   ├── Perro.java
│           │   ├── SolicitudAdopcion.java
│           │   └── Usuario.java
│           │
│           ├── sistema/
│           │   └── SistemaAlbergue.java
│           │
│           ├── persistencia/
│           │   └── PersistenciaDatos.java
│           │
│           └── interfaz/
│               ├── GestionAdopcion.java
│               ├── GestionDonacion.java
│               ├── GestionHistorialClinico.java
│               ├── GestionRegistroMascota.java
│               ├── InicioSesion.java
│               └── VentanaPrincipalAlbergue.java
│
├── albergue.json
├── pom.xml
└── README.md
```

**Nota:** La estructura representa los principales archivos y paquetes del proyecto. Los formularios gráficos de NetBeans cuentan también con sus respectivos archivos `.form`.

---

## Descripción de los paquetes

### `com.albergue.modelo`

Contiene las clases que representan las entidades principales del sistema.

- **Mascota:** clase abstracta que representa los datos generales de una mascota.
- **Perro:** clase derivada de `Mascota` que incorpora características específicas.
- **Adoptante:** representa a una persona interesada en adoptar.
- **SolicitudAdopcion:** almacena la información de las solicitudes de adopción.
- **HistorialClinico:** representa las atenciones médicas de una mascota.
- **Donacion:** clase padre que representa una donación.
- **Efectivo:** representa las donaciones monetarias.
- **Comida:** representa las donaciones de alimentos.
- **Usuario:** almacena los datos necesarios para la autenticación.
- **Autenticable:** interfaz que establece el contrato de autenticación.

### `com.albergue.sistema`

Contiene la clase `SistemaAlbergue`, encargada de administrar las operaciones principales del sistema.

Se encarga de:

- Registrar y administrar mascotas.
- Gestionar adoptantes.
- Crear y evaluar solicitudes de adopción.
- Administrar historiales clínicos.
- Registrar y eliminar donaciones.
- Calcular totales y realizar consultas.
- Coordinar las operaciones con las colecciones de datos.

### `com.albergue.persistencia`

Contiene la clase `PersistenciaDatos`, responsable de guardar y recuperar la información mediante archivos JSON utilizando Gson.

### `com.albergue.interfaz`

Contiene las ventanas y formularios gráficos desarrollados con Java Swing.

Incluye las interfaces de:

- Inicio de sesión.
- Ventana principal.
- Registro y gestión de mascotas.
- Gestión de adopciones.
- Gestión de historiales clínicos.
- Gestión de donaciones.

---

## Inicio de sesión

Para ingresar a la aplicación se utiliza una cuenta de administrador.

**Credenciales de acceso:**

```text
Usuario: admin
Contraseña: 1234
```

El sistema permite un máximo de **tres intentos de autenticación**. Si se agotan los intentos permitidos, se cierra la ventana de inicio de sesión.

> **Importante:** Las credenciales están definidas directamente en el código y tienen fines demostrativos y académicos. Para un entorno real se recomienda implementar una gestión segura de usuarios y contraseñas.

---

## Almacenamiento y persistencia de datos

El sistema utiliza colecciones de Java, principalmente `ArrayList`, para administrar la información durante la ejecución.

Además, implementa persistencia mediante la biblioteca **Gson**, que permite convertir los objetos Java al formato JSON y recuperar posteriormente la información almacenada.

El archivo utilizado para almacenar los datos es:

```text
albergue.json
```

El archivo contiene las siguientes colecciones principales:

| Colección | Información almacenada |
|---|---|
| `mascotas` | Datos de las mascotas e historiales clínicos |
| `adoptantes` | Información de las personas registradas |
| `solicitudes` | Solicitudes y estados de adopción |
| `donaciones` | Donaciones en efectivo y alimentos |

Los historiales clínicos se almacenan asociados a cada mascota.

Este mecanismo permite conservar la información entre diferentes ejecuciones de la aplicación, sin necesidad de utilizar una base de datos externa.

---

## Instalación y ejecución

### Requisitos previos

Antes de ejecutar el proyecto, es necesario contar con:

- JDK instalado.
- Apache NetBeans IDE.
- Biblioteca Gson configurada en el proyecto.

### Pasos para ejecutar

1. Clonar el repositorio:

```bash
git clone https://github.com/iFabrizio-z/albergue-mascotas.git
```

2. Abrir Apache NetBeans IDE.

3. Seleccionar **File → Open Project**.

4. Buscar y seleccionar la carpeta del proyecto.

5. Verificar que la biblioteca Gson esté correctamente agregada a las dependencias.

6. Ejecutar el proyecto desde la clase principal `App.java`.

7. Iniciar sesión con las credenciales proporcionadas.

---

## Conceptos de programación aplicados

El desarrollo del proyecto permite aplicar diferentes conceptos de programación orientada a objetos y estructuras de datos.

### Programación Orientada a Objetos (POO)

- Clases y objetos.
- Encapsulamiento.
- Abstracción.
- Herencia.
- Polimorfismo.
- Interfaces.
- Constructores.
- Métodos `getters` y `setters`.
- Asociación entre objetos.

### Estructuras de datos y procesamiento

- Colecciones mediante `ArrayList` y `List`.
- Búsqueda y procesamiento mediante Java Streams.
- Administración de registros.
- Relaciones entre entidades.

### Otros conceptos

- Manejo de excepciones.
- Validación de datos.
- Interfaces gráficas con Java Swing.
- Gestión de fechas con `LocalDate`.
- Serialización y deserialización con Gson.
- Persistencia de información mediante JSON.

---

## Posibles mejoras futuras

- Implementar una base de datos relacional.
- Incorporar diferentes roles de usuario.
- Mejorar la seguridad de las credenciales.
- Generar reportes de adopciones y donaciones.
- Incorporar fotografías de las mascotas.
- Implementar copias de seguridad automáticas.
- Agregar estadísticas generales del albergue.

---

## Proyecto académico

Proyecto desarrollado con fines académicos para aplicar conocimientos de **Programación Orientada a Objetos, estructuras de datos, interfaces gráficas y persistencia de información en Java**.

El sistema busca ofrecer una herramienta que facilite la organización y administración de un albergue de mascotas, contribuyendo al control de sus registros, procesos de adopción, atenciones clínicas y donaciones.

---

**Sistema de Gestión de Albergue de Mascotas**

*Desarrollado en Java con dedicación al bienestar y cuidado de las mascotas.*
