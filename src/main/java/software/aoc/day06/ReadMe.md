# Advent of Code - Día 06: Procesamiento de Matrices con Operadores

## 1. Introducción al Diseño
El reto de hoy consiste en resolver una matriz donde cada columna debe ser procesada por un operador específico (suma o multiplicación) definido en una línea aparte. [cite_start]Hemos aplicado un diseño que favorece el **Bajo Acoplamiento** y el **Código Expresivo**, permitiendo que el motor de cálculo sea agnóstico a la operación matemática específica que ejecuta[cite: 71, 74].



## 2. Abstracción de Operaciones: `Operator`
[cite_start]Para cumplir con el **Principio de Inversión de Dependencias (DIP)**, no utilizamos estructuras de control `if/else` para decidir la operación[cite: 91].
- [cite_start]**Polimorfismo en Enums**: Cada constante del Enum (`ADD`, `MULTIPLY`) implementa su propio método `apply` e `identity`[cite: 111].
- **Identidad Matemática**: Cada operador conoce su valor neutro (0 para suma, 1 para multiplicación), lo que permite que el acumulador se inicialice correctamente de forma dinámica.
- [cite_start]**Factoría Estática**: El método `fromChar` actúa como un **Factory Method** que traduce los símbolos del archivo a lógica ejecutable[cite: 106, 142].

## 3. Integridad de Datos: `ProblemSchema`
[cite_start]La cohesión del sistema se garantiza mediante el uso de **Records** de Java, que actúan como estructuras inmutables[cite: 70, 75].
- **Validación de Consistencia**: El constructor de `ProblemSchema` actúa como una "guarda", verificando que el ancho de la matriz coincida exactamente con el número de operadores proporcionados.
- [cite_start]**Prevención de Errores**: Este diseño asegura que el sistema falle inmediatamente si el archivo de entrada tiene dimensiones incorrectas, cumpliendo con el fundamento de **Alta Cohesión**[cite: 70].

## 4. El Servicio de Cálculo de Columnas
El `ColumnCalculationService` es el responsable de orquestar la resolución.
- **Algoritmo Genérico**: El servicio itera por columnas y filas sin conocer la naturaleza de la operación. [cite_start]Simplemente solicita al operador que "aplique" su lógica al acumulador[cite: 85, 111].
- [cite_start]**Encapsulamiento**: La lógica de acceso a la matriz está centralizada en la clase `Matrix`, evitando que el servicio conozca la estructura interna de las listas anidadas (Ley de Demeter)[cite: 94].



## 5. Fundamentos SOLID Aplicados
- [cite_start]**Responsabilidad Única (SRP)**: El parser entiende el formato del archivo, el operador entiende la matemática y el servicio entiende el flujo de cálculo.
- [cite_start]**Abierto/Cerrado (OCP)**: El sistema está **abierto a la extensión**; añadir nuevos operadores (como resta o potencia) no requiere modificar el servicio de cálculo ni la estructura de la matriz[cite: 86, 87].
- [cite_start]**Modularidad**: Cada componente es independiente, facilitando la mantenibilidad y la escalabilidad del sistema[cite: 72].

---
*Este enfoque transforma un problema matricial en un motor de procesamiento extensible, donde la lógica de negocio y la infraestructura de datos conviven de forma desacoplada.*
---

# Parte B: Escaneo Vertical y Bloques Dinámicos

## 1. Introducción: El Desafío del Barrido Vertical
En esta fase, la estructura del problema cambia radicalmente. Los números ya no están en celdas individuales, sino que se forman verticalmente concatenando dígitos de varias filas. Además, estos números se agrupan en "bloques" separados por columnas vacías. Para resolver esto, hemos implementado un **GridScanner** que realiza un barrido columna a columna para reconstruir el dominio.



## 2. El Motor de Segmentación: `GridScanner`
Esta clase es el núcleo de la infraestructura y refleja una alta **Cohesión**:
- **Extracción de Columnas**: Mediante `extractColumn`, el sistema mira hacia abajo en cada índice, recolectando dígitos para formar números largos y detectando el operador asociado.
- **Detección de Bloques**: El escáner mantiene un estado temporal. Cuando detecta una columna vacía, "corta" el bloque actual, lo empaqueta en un `NumberBlock` y reinicia el proceso.
- **Robustez**: Maneja longitudes de fila desiguales y huecos, asegurando que solo los caracteres válidos se conviertan en datos de negocio.

## 3. Lógica de Negocio en `Operation
Siguiendo el **Principio de Inversión de Dependencias (DIP)**, la lógica de decisión se ha movido a una abstracción:
- **Firma de Bloque**: El sistema recolecta todos los operadores vistos bajo un bloque. El método `determineFromSignature` aplica la regla crítica: si existe al menos un `*`, la operación es multiplicación; de lo contrario, es suma.
- **Estrategias Funcionales**: Al usar `LongBinaryOperator`, el Enum no solo nombra la operación, sino que la ejecuta, facilitando el uso de Streams para la reducción final.

## 4. Inmutabilidad y Seguridad
Para garantizar la calidad y mantenibilidad del sistema:
- **Records e Inmutabilidad**: `NumberBlock` utiliza la característica de Record de Java y aplica una **copia defensiva** (`List.copyOf`) en su constructor. Esto asegura que una vez que el bloque sale del escáner, nadie pueda alterar sus números.
- **Manejo de Nulos**: Se utiliza `Optional<Long>` para representar la posibilidad de que una columna no contenga un número, eliminando el riesgo de `NullPointerException` durante el barrido vertical.



## 5. Fundamentos de Software Aplicados
- **Responsabilidad Única (SRP)**: El `ProblemSolver` se limita a coordinar, mientras que el `GridScanner` resuelve la complejidad del formato físico.
- **Abierto/Cerrado (OCP)**: Si el reto añadiera nuevos operadores o reglas de prioridad, solo necesitaríamos extender el Enum `Operation` sin modificar el motor de escaneo.
- **Bajo Acoplamiento**: Los componentes se comunican a través de Records simples, lo que permite cambiar la lógica del escáner (ej: para leer en diagonal) sin afectar al cálculo de los bloques.

---
*Esta arquitectura convierte un problema de parsing visual complejo en un flujo de datos estructurado, inmutable y fácil de escalar.*