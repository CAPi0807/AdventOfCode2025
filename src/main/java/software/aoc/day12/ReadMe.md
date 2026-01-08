# Advent of Code - Día 12: Christmas Packing (2D Bin Packing / Tiling)

Este proyecto resuelve un problema de **Backtracking** para determinar si un conjunto de figuras tipo "Tetris" (poliominós) puede encajar perfectamente en una cuadrícula de dimensiones dadas sin solaparse.

## Principios de Diseño y Arquitectura

### 1. Modelado de Figuras (`Shape`)
Una figura se define por un conjunto de coordenadas relativas.
- **Inmutabilidad**: La clase `Shape` es inmutable.
- **Normalización**: Al crearse, las coordenadas se normalizan (se desplazan para empezar en `0,0`), lo que simplifica la lógica de colocación.
- **Pre-cálculo de Rotaciones**: Dado que probar orientaciones es parte del algoritmo, cada figura pre-calcula sus rotaciones únicas (0°, 90°, 180°, 270°) en el constructor. Esto evita cálculos redundantes durante la recursión intensiva.

### 2. Estructura de Datos (`Grid`)
Representa el tablero de juego.
- Utiliza una matriz booleana (`boolean[][]`) para eficiencia máxima.
- **Single Responsibility**: Solo sabe verificar si una posición está libre (`canPlace`) y actualizar su estado (`place`/`remove`). No contiene lógica de búsqueda.

### 3. Algoritmo de Resolución (`PuzzleSolver`)
Implementa un **Backtracking Recursivo** con optimizaciones (Pruning):
1.  **Chequeo de Área**: Si la suma de las áreas de las piezas es mayor que el área del tablero, retorna `false` inmediatamente.
2.  **Heurística de Ordenamiento**: Intenta colocar primero las piezas más grandes. Esto suele hacer que el backtracking falle más rápido en ramas imposibles (fail-fast).
3.  **Recursión**:
    - Intenta colocar la pieza actual en cada celda libre.
    - Prueba todas las rotaciones posibles.
    - Si encaja, actualiza el grid y llama recursivamente para la siguiente pieza.
    - Si la recursión falla, hace "backtrack" (libera el espacio y prueba lo siguiente).

### 4. Parsing Robusto (`InputParser`)
Maneja un formato de archivo mixto que contiene definiciones de figuras (con arte ASCII) y casos de prueba en la misma entrada.

## Documentación de Archivos

### 1. `Main.java`
* **Función**: Punto de entrada.
* **Detalles**: Lee el archivo, obtiene el dataset completo y ejecuta el solver para cada caso de prueba, contando cuántos son válidos.

### 2. `Coordinate.java`
* **Función**: Value Object (Record).
* **Detalles**: Representa `(fila, columna)`. Incluye método `add` para aritmética vectorial simple.

### 3. `Shape.java`
* **Función**: Dominio Rico.
* **Detalles**:
    - `normalize()`: Mueve la figura al origen.
    - `rotate()`: Aplica matriz de rotación 90 grados $(x,y) \to (y, -x)$.
    - `generateUniqueRotations()`: Filtra rotaciones simétricas (ej: rotar un cuadrado 90 grados da la misma forma, no hace falta probarlo 4 veces).

### 4. `Grid.java`
* **Función**: Estado Mutable Controlado.
* **Detalles**: Provee métodos atómicos `place` y `remove` para facilitar el backtracking.

### 5. `InputParser.java`
* **Función**: Lógica de Texto Compleja.
* **Detalles**:
    - Detecta secciones de "definición" (ID + mapa de `#`) y secciones de "test" (`NxM: ...`).
    - Construye la lista plana de objetos `Shape` requerida para cada test.