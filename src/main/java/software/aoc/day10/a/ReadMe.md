# Advent of Code - Día 10: Lights Out (Linear Algebra GF(2))

Este proyecto resuelve un problema de optimización de estados (luces encendidas/apagadas) modelándolo como un sistema de ecuaciones lineales sobre un campo finito de orden 2 (GF(2)), también conocido como aritmética modular binaria.

## Principios de Diseño

### SOLID & Clean Code
1.  **Single Responsibility Principle (SRP)**:
    - `MachineParser`: Única responsabilidad de interpretar cadenas de texto y convertirlas en objetos.
    - `GaussianSolver`: Única responsabilidad de resolver matrices matemáticas puras, sin conocimiento del dominio "luces" o "botones".
    - `Day10Solver`: Responsable de traducir el dominio de negocio (Objetos `Machine`) al dominio matemático.
2.  **Inmutabilidad**:
    - Se utiliza `record` para la clase `Machine`, garantizando que el estado de entrada no cambie durante la ejecución.
3.  **Separación de Niveles de Abstracción**:
    - El `Main` orquesta.
    - El `Day10Solver` actúa como capa de servicio.
    - El `GaussianSolver` actúa como núcleo algorítmico de bajo nivel.

## Documentación de Archivos

### 1. `Main.java`
* **Función General**: Punto de entrada de la aplicación.
* **Detalles**:
    - Configura las dependencias (`Parser`, `Solver`).
    - Gestiona la lectura del archivo de recursos (`Machines.txt`) usando Streams para eficiencia de memoria.
    - Filtra líneas vacías e imprime el resultado final acumulado.

### 2. `Machine.java`
* **Función General**: DTO (Data Transfer Object) inmutable que representa el estado de una máquina.
* **Detalles**:
    - **`targetState`**: Lista de enteros (0 o 1) representando el estado deseado de las luces.
    - **`buttons`**: Lista de listas, donde cada sublista contiene los índices de las luces que un botón específico alterna.
    - **`voltageTargets`**: Datos adicionales del input (no usados en la lógica de minimización de pulsaciones, pero parseados por completitud).
    - Métodos auxiliares (`lightCount`, etc.) para legibilidad.

### 3. `MachineParser.java`
* **Función General**: Motor de expresiones regulares para convertir texto crudo en objetos `Machine`.
* **Detalles**:
    - **`PATTERNS`**: Define regex compilados estáticamente para extraer secciones `[...]`, `(...)` y `{...}`.
    - **`parseSequence`**: Extrae secuencias numéricas o mapas de caracteres (convirtiendo `#` a `1` y `.` a `0`).
    - **`parseButtons`**: Itera sobre los grupos de captura para construir la lista de conexiones de los botones.

### 4. `GaussianSolver.java`
* **Función General**: Motor matemático. Resuelve $Ax = b$ donde los valores son binarios y la suma es XOR.
* **Detalles**:
    - **`solveMinimumPresses`**: Orquesta el algoritmo. Construye la matriz aumentada y llama a la eliminación.
    - **Eliminación Gaussiana**: Convierte la matriz a su forma escalonada reducida por filas usando operaciones XOR en lugar de restas.
    - **Manejo de Variables Libres**: Esta es la parte crítica. Si el sistema tiene múltiples soluciones (sistema subdeterminado), identifica las columnas que no son pivotes (variables libres).
    - **`findMinWeightSolution`**: Prueba recursiva (o iterativa bit a bit) de todas las combinaciones de valores (0/1) para las variables libres para encontrar la solución que requiere menos "1s" (menos pulsaciones de botones).

### 5. `Day10Solver.java`
* **Función General**: Adaptador de dominio.
* **Detalles**:
    - **`solveMachine`**: Transforma el objeto `Machine` en una matriz de adyacencia `int[][]`.
        - Las filas representan las luces.
        - Las columnas representan los botones.
        - Si el botón $j$ afecta a la luz $i$, entonces $M[i][j] = 1$.
    - Delega la resolución al `GaussianSolver` y filtra resultados imposibles (-1).