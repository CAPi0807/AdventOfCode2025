# Advent of Code - Día 10 Parte B: Optimización de Voltaje

En la Parte B, el problema cambia de naturaleza matemática. Ya no estamos en un campo finito binario (0/1), sino en el dominio de los números reales/enteros positivos. Cada pulsación de botón aumenta el voltaje; buscamos la combinación de enteros no negativos $(x_1, x_2, \dots)$ que satisfaga el sistema $Ax = b$ minimizando $\sum x_i$.

## Principios de Diseño

### Reutilización y Modularidad
- Se reutilizan las clases `Machine` y `MachineParser` del paquete `a`. Esto demuestra la solidez del diseño original: el parsing y el DTO son agnósticos al tipo de resolución matemática.
- Se introducen nuevas clases en el paquete `b` solo donde la lógica diverge (Resolución matemática).

### Algoritmo: Programación Lineal Entera (Simplificada)
El problema se modela como un sistema de ecuaciones lineales. Dado que las entradas suelen ser pequeñas o tener pocas "variables libres", utilizamos un enfoque híbrido:
1.  **Eliminación de Gauss-Jordan (RREF)**: Utilizamos `double` para reducir la matriz. Esto identifica las variables dependientes (pivotes) y las independientes (libres).
2.  **Búsqueda en Espacio de Soluciones**: Iteramos sobre posibles valores enteros para las variables libres.
3.  **Validación de Enteros**: Dado que Gauss trabaja con flotantes, verificamos si los valores resultantes para las variables pivote son enteros positivos (dentro de un margen de error `EPSILON`).

## Documentación de Archivos

### 1. `Main.java`
* **Función**: Punto de entrada específico para la Parte B.
* **Detalles**:
    - Reutiliza el `MachineParser` de la parte A.
    - Delega la lógica de negocio al `Day10BSolver`.
    - Imprime la suma total de pulsaciones mínimas requeridas.

### 2. `Day10BSolver.java` (Adapter Pattern)
* **Función**: Transforma el objeto de dominio `Machine` en estructuras matemáticas.
* **Detalles**:
    - Convierte la lista de conexiones de botones en una matriz `double[][]`.
    - Convierte los objetivos de voltaje en un vector `double[]`.
    - Actúa como puente entre el dominio del problema (Advent of Code) y el dominio matemático (`LinearOptimizer`).
    - Maneja el caso de "sin solución" (-1) convirtiéndolo a 0 para la suma total.

### 3. `LinearOptimizer.java` (Core Logic)
* **Función**: Resuelve sistemas de ecuaciones lineales buscando soluciones enteras no negativas mínimas.
* **Flujo del Algoritmo**:

    1.  **Heurística de Límites**: Calcula un límite de búsqueda basado en el valor objetivo máximo (`maxTarget + 2`). Como los coeficientes son positivos (solo sumamos voltaje), ninguna variable individual debería exceder el objetivo.
    2.  **Reducción**: Aplica Gauss-Jordan con pivoteo parcial para estabilidad numérica.
    3.  **Identificación**: Separa variables libres de variables pivote.
    4.  **Búsqueda Exhaustiva Acotada**: Prueba combinaciones de valores para las variables libres. Calcula las variables pivote resultantes.
    5.  **Minimización**: Si una solución es válida (todos enteros $\ge 0$), compara la suma total con el mínimo encontrado hasta el momento.