# Advent of Code - Día 11: Graph Path Counting

Este proyecto resuelve un problema de teoría de grafos: contar el número total de caminos distintos desde un nodo origen ("you") hasta un nodo destino ("out") en un Grafo Acíclico Dirigido (DAG).

## Principios de Diseño y Arquitectura

### 1. Separation of Concerns (SoC) & SRP
La lógica se ha dividido estrictamente para facilitar el mantenimiento y las pruebas:
- **Parsing**: `GraphParser` convierte texto crudo en una estructura de datos. No sabe nada de algoritmos.
- **Datos**: `Graph` es un contenedor puro de la estructura de adyacencia.
- **Algoritmo**: `PathCounter` contiene la lógica recursiva y la optimización (memoización).
- **Negocio**: `Day11Solver` conoce los requisitos específicos del problema ("you" -> "out").

### 2. Algoritmo: DFS con Memoización (Programación Dinámica)
El problema de contar caminos en un grafo puede tener una complejidad exponencial si se hace con fuerza bruta ($O(2^N)$), ya que muchos sub-caminos se repiten.
- Se utiliza **Depth First Search (DFS)** para explorar.
- Se aplica **Memoización**: Guardamos el resultado de `countPaths(nodo)` en un mapa `memo`. Si volvemos a llegar a ese nodo por otra ruta, devolvemos el valor calculado instantáneamente.
- Esto reduce la complejidad drásticamente, permitiendo resolver grafos grandes en tiempo casi lineal respecto a las aristas.

### 3. Inmutabilidad
- La clase `Graph` es inmutable. Recibe el mapa de adyacencia en el constructor y realiza una `Map.copyOf` (copia defensiva) para evitar modificaciones externas posteriores.

## Documentación de Archivos

### 1. `Main.java`
* **Función**: Punto de entrada (Entry Point).
* **Responsabilidad**:
    - Gestionar la lectura del archivo `Connections.txt` de forma segura (try-with-resources).
    - Instanciar el solver y mostrar el resultado en consola.

### 2. `Graph.java`
* **Función**: Estructura de datos (Wrapper del Grafo).
* **Responsabilidad**:
    - Almacenar la lista de adyacencia (`Map<String, List<String>>`).
    - Proveer acceso seguro a los vecinos de un nodo (`getNeighbors`). Devuelve una lista vacía si el nodo no tiene salientes, evitando `NullPointerException`.

### 3. `GraphParser.java`
* **Función**: Traductor de Texto a Objeto.
* **Responsabilidad**:
    - Interpretar el formato `origen: destino1 destino2`.
    - Limpiar espacios en blanco y filtrar líneas vacías.
    - Construir y retornar el objeto `Graph`.

### 4. `PathCounter.java`
* **Función**: Motor algorítmico.
* **Responsabilidad**:
    - **`countPaths`**: Método público que inicializa la caché y lanza la recursión.
    - **`countPathsRecursive`**:
        1.  **Caso Base**: Si `current == target`, retornamos 1 (hemos encontrado 1 camino válido).
        2.  **Caché**: Si ya calculamos cuántos caminos hay desde `current` hasta el final, lo devolvemos.
        3.  **Recursión**: Sumamos los caminos de todos los vecinos.
        4.  **Almacenamiento**: Guardamos el resultado en `memo` antes de retornar.

### 5. `Day11Solver.java`
* **Función**: Fachada de Servicio.
* **Responsabilidad**:
    - Orquesta el flujo: Llama al parser -> Obtiene el Grafo -> Instancia el Counter.
    - Define los parámetros del problema (ir de "you" a "out").