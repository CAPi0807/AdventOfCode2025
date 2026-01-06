# Advent of Code Day 09 (Parte B)

La Parte B resuelve el problema de "Rectángulo máximo dentro de un polígono arbitrario".

## Arquitectura y Principios

### 1. Reutilización (DRY)
- Se reutilizan `Point` y `BoundingBox` de la Parte A (paquete `software.aoc.day09.a`).
- Se reutiliza el `PointParser`.

### 2. Separación de Responsabilidades (SRP)
- **`RayCaster`**: Contiene exclusivamente la matemática del algoritmo "Point in Polygon" (Even-Odd rule). No sabe nada de grids ni rectángulos.
- **`CompressedGrid`**: Gestiona la discretización del espacio. Transforma coordenadas continuas en celdas lógicas y mantiene el mapa de "solidez" (`boolean[][]`).
- **`ConstrainedSolver`**: Coordina la generación de candidatos (fuerza bruta de pares) y su validación contra el grid.

### 3. Inmutabilidad
- El Grid Comprimido se calcula una vez en el constructor y es inmutable.
- Los Records `Point` y `BoundingBox` garantizan integridad de datos.

### 4. Algoritmo de Compresión de Coordenadas
Para evitar crear una matriz gigante si las coordenadas son muy grandes (ej: 0 a 1,000,000), usamos **Coordinate Compression**:
1. Extraemos los valores únicos de X e Y.
2. Los ordenamos.
3. Esto crea una rejilla irregular donde cada celda representa un área rectangular del espacio real.
4. Solo verificamos si el centro de esa celda lógica está dentro del polígono.