# Advent of Code Day 09

## Principios Implementados

### 1. Domain-Driven Design (DDD) Ligero
- Usamos el record `BoundingBox`.
- `BoundingBox` es dueño de la geometría. Si la definición de "área" cambia, solo cambiamos este record.

### 2. Single Responsibility Principle (SRP)
- **`PointParser`**: Solo convierte texto a Puntos.
- **`BruteForcePairsStrategy`**: Solo implementa el algoritmo de combinación de pares.
- **`GeometryService`**: Solo coordina la entrada y la salida.

### 3. Open/Closed Principle (OCP)
- Se define la interfaz `AreaStrategy`.
- Actualmente usamos `BruteForcePairsStrategy`. Si quisiéramos implementar una solución más óptima (O(N) encontrando solo minX, maxX, minY, maxY), crearíamos `ExtremesStrategy` sin modificar el `Point` ni el `Service`.

### 4. Inmutabilidad
- `Point` y `BoundingBox` son Records inmutables.
- No hay estado compartido mutable durante el cálculo.