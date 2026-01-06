# Advent of Code Day 08

## Principios Implementados

### 1. Single Responsibility Principle (SRP)
- **`DisjointSet`**: Es una estructura de datos pura. Solo sabe de uniones y búsquedas de raíces. No sabe de coordenadas ni distancias.
- **`EuclideanStrategy`**: Solo sabe calcular la distancia entre dos puntos 3D.
- **`NetworkService`**: Orquesta la lógica de negocio (generar grafo, ordenar aristas, aplicar límite de conexiones).
- **`CoordinateParser`**: Solo parsea strings a objetos.

### 2. Open/Closed Principle (OCP)
- Se utiliza la interfaz `DistanceStrategy`.
- El sistema actualmente usa distancia Euclidiana, pero está preparado para usar distancia Manhattan o Chebyshev sin modificar `NetworkService` ni `DisjointSet`, simplemente pasando una nueva implementación al constructor del servicio.

### 3. Inmutabilidad
- `Point3D` y `Edge` son **Java Records**.
- Son objetos inmutables que representan el estado del dominio en un momento dado.
- Esto elimina errores de efectos secundarios al pasar puntos entre métodos.

### 4. Cohesión
El código original (`CircuitSolver`) mezclaba parsing, geometría y lógica de grafos. Ahora cada aspecto vive en su propio módulo, haciendo el código más testea ble y mantenible.