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