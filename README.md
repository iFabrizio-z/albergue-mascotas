#  Sistema de Albergue de Mascotas

Sistema desarrollado en **Java** para la gestión básica de un albergue de mascotas.
El proyecto permite administrar mascotas, solicitudes de adopción, historiales clínicos y donaciones mediante una aplicación de consola.

## Descripción

El **Sistema de Albergue de Mascotas** busca facilitar la administración de la información de las mascotas que se encuentran en un albergue.

El sistema permite:

* Registrar mascotas.
* Consultar mascotas mediante su ID.
* Registrar solicitudes de adopción.
* Evaluar y aprobar o rechazar solicitudes.
* Actualizar el estado de adopción de las mascotas.
* Registrar historiales clínicos.
* Consultar el historial clínico de una mascota.
* Registrar donaciones en efectivo o comida.
* Consultar el historial y totales de donaciones.
* Proteger el acceso mediante un inicio de sesión.

## Tecnologías utilizadas

* **Java**
* **Java Collections Framework**

  * `ArrayList`
  * `List`
* **Java Streams**
* **Java Time API**

  * `LocalDate`
* **Visual Studio Code**
* **JDK**

##  Estructura del proyecto

```text
albergue-mascota/
│
├── src/
│   ├── Adoptante.java
│   ├── App.java
│   ├── Donacion.java
│   ├── HistorialClinico.java
│   ├── Mascota.java
│   ├── SistemaAlbergueMascota.java
│   ├── SolicitudAdopcion.java
│   └── Usuario.java
│
├── bin/
│   └── Archivos compilados (.class)
│
├── .vscode/
│   └── settings.json
│
└── README.md
```

## Clases principales

### `App`

Es la clase principal del programa.

Se encarga de:

* Iniciar la aplicación.
* Solicitar el inicio de sesión.
* Mostrar el menú principal.
* Gestionar las opciones seleccionadas por el usuario.

### `Adoptante`

Representa a un persona que busca adoptar a una mascota del albergue.

Incluye los siguientes datos:
* Nombre
* Apellidos
* DNI
* Dirección
* Email
* Teléfono

### `Mascota`

Representa a una mascota registrada en el albergue.

Contiene información como:

* ID
* Nombre
* Raza
* Edad
* Género
* Peso
* Tamaño
* Estado de adopción
* Historial clínico

Los estados de adopción utilizados son:

* `Refugiado`
* `Tratamiento`
* `Adoptado`

### `SolicitudAdopcion`

Representa una solicitud realizada por una persona interesada en adoptar una mascota.

Registra:

* ID de solicitud
* Fecha
* Estado
* Nombre y apellidos del adoptante
* DNI
* Dirección
* Correo electrónico
* Teléfono
* Mascota solicitada

Los estados de una solicitud pueden ser:

* `Pendiente`
* `Aprobada`
* `Rechazada`

### `HistorialClinico`

Permite registrar las atenciones médicas realizadas a una mascota.

Incluye:

* ID del historial
* Fecha de atención
* Tipo de procedimiento
* Diagnóstico
* Tratamiento
* Nombre del veterinario

### `Donacion`

Permite registrar donaciones realizadas al albergue.

El sistema admite dos tipos:

* **Efectivo**
* **Comida para mascotas**

En el caso de dinero se registra el monto en soles, mientras que las donaciones de comida se registran en kilogramos.

### `Usuario`

Gestiona la autenticación del usuario administrador mediante:

* Usuario
* Contraseña
* Nombre

## Inicio de sesión

Para ingresar al sistema se utiliza un usuario administrador.

**Credenciales actuales:**

```text
Usuario: admin
Contraseña: 1234
```

> Estas credenciales están definidas directamente en el código y se utilizan únicamente para el funcionamiento actual del proyecto.

## Funcionalidades

Después de iniciar sesión se muestra el siguiente menú:

```text
-------SISTEMA ALBERGUE MASCOTAS-------
1. Registrar Mascota
2. Ver Datos de Mascota por Id
3. Crear solicitud de adopción
4. Evaluar solicitud pendiente
5. Agregar historial clínico por id de mascota
6. Mostrar historial clínico por id de mascota
7. Registrar Donación
8. Listar Donaciones
9. Salir
```

### 1. Registrar mascota

Permite ingresar los datos de una nueva mascota:

* ID
* Nombre
* Raza
* Edad
* Género
* Peso
* Tamaño

La mascota se registra inicialmente con estado **Refugiado**.

### 2. Buscar mascota

Permite consultar los datos de una mascota utilizando su ID.

### 3. Crear solicitud de adopción

Permite registrar una solicitud asociada a una mascota existente.

Se solicita información del adoptante como:

* Nombre
* Apellidos
* DNI
* Dirección
* Email
* Teléfono

La solicitud se crea inicialmente con estado **Pendiente**.

### 4. Evaluar solicitud

Permite buscar una solicitud por ID y decidir entre:

```text
1. Aprobar
2. Rechazar
```

Cuando una solicitud es aprobada, el estado de la mascota cambia automáticamente a:

```text
Adoptado
```

### 5. Agregar historial clínico

Permite registrar una atención médica asociada a una mascota.

Se registra:

* Procedimiento
* Tratamiento
* Diagnóstico
* Veterinario

La fecha se genera automáticamente utilizando `LocalDate`.

### 6. Mostrar historial clínico

Permite consultar todos los registros médicos asociados a una mascota mediante su ID.

### 7. Registrar donación

Permite registrar dos tipos de donaciones:

```text
1. Efectivo
2. Comida para mascotas (Kg)
```

El sistema valida que los valores ingresados sean mayores que cero.

### 8. Listar donaciones

Muestra las donaciones registradas y genera un resumen con:

* Total recaudado en efectivo.
* Total de comida recibida en kilogramos.

## Cómo ejecutar el proyecto

### Requisitos

Se necesita tener instalado:

* **JDK**
* **Visual Studio Code** (opcional)

### Desde Visual Studio Code

1. Abrir la carpeta:

```text
albergue-mascota
```

2. Abrir la carpeta `src`.
3. Ejecutar `App.java`.
4. Ingresar las credenciales:

```text
Usuario: admin
Contraseña: 1234
```

5. Utilizar el menú de opciones.

### Desde la terminal

Ubicándose dentro de la carpeta del proyecto:

```bash
javac -d bin src/*.java
```

Luego ejecutar:

```bash
java -cp bin App
```

##  Conceptos de programación utilizados

El proyecto aplica diferentes conceptos de **Programación Orientada a Objetos (POO)**:

* Clases y objetos.
* Encapsulamiento.
* Atributos privados.
* Constructores.
* Métodos `getter` y `setter`.
* Composición entre objetos.
* Sobrecarga de constructores.
* Colecciones mediante `ArrayList`.
* Búsqueda mediante Java Streams.
* Manejo de excepciones.
* Validación de datos.
* Uso de fechas con `LocalDate`.

## Almacenamiento de información

Actualmente, la información se mantiene **en memoria** utilizando listas (`ArrayList`).

Por esta razón, los registros creados durante la ejecución se pierden cuando el programa se cierra.

El proyecto no utiliza actualmente una base de datos.

## Proyecto académico

Proyecto desarrollado con fines académicos para aplicar conceptos de **Programación Orientada a Objetos y desarrollo de sistemas en Java**.

---

**Sistema de Albergue de Mascotas **

