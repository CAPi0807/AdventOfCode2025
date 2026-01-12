# Advent of Code - Día 06: Procesamiento de Matrices con Operadores

## 1. Introducción al Diseño

El reto de hoy consiste en resolver una matriz donde cada columna debe ser procesada por un operador específico (suma o multiplicación) definido en una línea aparte. Hemos aplicado un diseño que favorece el **Bajo Acoplamiento**, la **Alta Cohesión** y el **Código Expresivo**, permitiendo que el motor de cálculo sea completamente agnóstico a la operación matemática específica que ejecuta.

Para lograrlo, el sistema se divide claramente en tres responsabilidades: **parsing del input**, **modelado del dominio** y **ejecución del cálculo**.

## 2. Abstracción de Operaciones: `Operator`

Para cumplir con el **Principio de Inversión de Dependencias (DIP)**, evitamos por completo el uso de estructuras de control como `if/else` o `switch` para decidir qué operación aplicar.

### Diseño del Enum `Operator`

El Enum `Operator` no es un simple listado de constantes, sino una **abstracción polimórfica de comportamiento matemático**:

* **Polimorfismo en Enums**: Cada constante (`ADD`, `MULTIPLY`) implementa su propia versión de los métodos `apply` e `identity`, encapsulando la lógica matemática.
* **Identidad Matemática**: Cada operador conoce su valor neutro:
    * `ADD` → `0` (0 + x = x)
    * `MULTIPLY` → `1` (1 * x = x)
      Esto permite inicializar acumuladores de forma genérica, sin conocimiento previo de la operación.
* **Ejecución Directa**: El método `apply(long acc, long value)` permite usar el operador directamente dentro de algoritmos de reducción o bucles.
* **Factoría Estática**: El método `fromChar` actúa como un **Factory Method**, traduciendo caracteres del input (`+`, `*`) a lógica ejecutable.
* **Validación Centralizada**: `isOperatorChar` define explícitamente qué símbolos son operadores válidos, evitando duplicar reglas en el parser.

Este diseño hace que añadir un nuevo operador (por ejemplo, resta o potencia) solo requiera añadir una nueva constante al Enum, sin modificar el resto del sistema.

## 3. Integridad de Datos: `ProblemSchema`

La cohesión del sistema se garantiza mediante el uso de **Records** de Java, que actúan como estructuras de datos inmutables y seguras.

* **Alta Cohesión**: `ProblemSchema` agrupa exclusivamente los datos que definen un problema válido: matriz + operadores.

## 4. Parsing del Input: `ProblemParser`

El parsing del archivo de entrada está completamente aislado en la clase `ProblemParser`, cumpliendo estrictamente el **Principio de Responsabilidad Única (SRP)**.

### Responsabilidades del Parser

* **Detección de la línea de operadores**: Mediante `findOperatorLineIndex`, el parser identifica la primera línea que contiene símbolos de operación (`+` o `*`).
* **Parseo de la matriz**: Todas las líneas anteriores se interpretan como filas de la matriz numérica.
* **Parseo de operadores**: La línea de operadores se convierte en una lista tipada de `Operator` usando `Operator.fromChar`.

## 5. El Servicio de Cálculo de Columnas

El `ColumnCalculationService` es el responsable de orquestar la resolución del problema.

* **Algoritmo Genérico**: Itera por columnas y filas sin conocer la naturaleza de la operación. Simplemente solicita al operador que aplique su lógica. Cuando hacemos el `identify()`, el objeto del enum que creamos ya es uno u otro, así que siempre aplicaremos el `apply()` correspondiente.
* **Encapsulamiento**: El acceso a los datos está centralizado en `Matrix`, cumpliendo la **Ley de Demeter**.
* **Reutilización**: El mismo motor funciona con cualquier combinación de operadores soportados.

## 6. Fundamentos SOLID Aplicados

* **Responsabilidad Única (SRP)**:

    * `ProblemParser`: entiende el formato del archivo.
    * `Operator`: entiende la matemática.
    * `ColumnCalculationService`: entiende el flujo de cálculo.
* **Abierto/Cerrado (OCP)**: El sistema está abierto a la extensión mediante nuevos operadores, sin modificar código existente.
* **Bajo Acoplamiento**: Los componentes se comunican mediante Records y Enums bien definidos.
* **Alta Cohesión**: Cada clase tiene un único motivo para cambiar.

---

*Este enfoque transforma un problema matricial en un motor de procesamiento extensible, robusto y fácil de mantener, donde el parsing, el dominio y la lógica de cálculo conviven de forma claramente desacoplada.*

---

# Parte B: Escaneo Vertical y Bloques Dinámicos

## 1. Introducción: El Desafío del Barrido Vertical

En esta fase, la estructura del problema cambia radicalmente. Los números ya no están en celdas individuales, sino que se forman verticalmente concatenando dígitos de varias filas. Además, estos números se agrupan en **bloques** separados por columnas vacías.

Para resolver este formato visual complejo, se ha implementado un `GridScanner` que reconstruye el dominio mediante un barrido columna a columna.

## 2. El Motor de Segmentación: `GridScanner`

Esta clase es el núcleo de la infraestructura y refleja una alta **Cohesión**:

* **Extracción de Columnas**: A través de `extractColumn`, el sistema recorre verticalmente cada índice, recolectando dígitos para formar números completos.
* **Detección de Bloques**: El escáner mantiene un estado temporal. Cuando detecta una columna vacía, finaliza el bloque actual y lo encapsula en un `NumberBlock`.
* **Robustez**: Maneja longitudes de fila desiguales y huecos, garantizando que solo datos válidos se conviertan en entidades de dominio.

## 3. Lógica de Negocio en `Operation`

Siguiendo el **Principio de Inversión de Dependencias (DIP)**:

* **Firma del Bloque**: El sistema recolecta todos los operadores encontrados bajo un bloque.
* **Regla de Decisión**: `determineFromSignature` aplica una regla clara: si existe al menos un `*`, la operación es multiplicación; en caso contrario, es suma.
* **Estrategias Funcionales**: El uso de `LongBinaryOperator` permite ejecutar la operación directamente durante reducciones con Streams.

## 4. Inmutabilidad y Seguridad

* **Records e Inmutabilidad**: `NumberBlock` es un Record que aplica copias defensivas (`List.copyOf`) para evitar mutaciones externas.
* **Uso de Optional**: `Optional<Long>` representa columnas sin número, eliminando la posibilidad de `NullPointerException` durante el escaneo.

## 5. Fundamentos de Software Aplicados

* **SRP**: El `ProblemSolver` coordina; el `GridScanner` interpreta el formato físico.
* **OCP**: Nuevas reglas u operadores pueden añadirse extendiendo Enums, sin modificar el motor.
* **Extensibilidad**: El sistema puede adaptarse fácilmente a nuevos patrones de lectura (diagonal, zigzag, etc.).

---

*¿Pude haber encontrado una solución más simple? Probablemente, pero estaba muy ofuscado. Odio este ejercicio, ojalá no caiga en el examen*
