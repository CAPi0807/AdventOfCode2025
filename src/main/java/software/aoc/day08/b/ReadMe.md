# Advent of Code Day 08 (Parte B)

En la Parte B, resolvemos el problema del **Árbol de Expansión Mínima (MST)** completo.

## Diseño Implementado

### 1. Detección de Estado Global (`DisjointSet`)
Se ha añadido la variable `componentCount` a la estructura Union-Find.
- Cada vez que una unión es exitosa (`union` devuelve `true`), decrementamos el contador.
- Esto permite saber en O(1) si el grafo está totalmente conectado (`count == 1`).

### 2. Servicio de Completado (`CircuitCompletionService`)
Implementa una variación del **Algoritmo de Kruskal**.
- Ordena todas las aristas por peso.
- Itera hasta que la condición de parada (`dsu.getComponentCount() == 1`) se cumple.
- Esto garantiza que la arista que dispara la condición de parada es la más pequeña necesaria para conectar los dos últimos sub-grafos disjuntos.

### 3. Inmutabilidad y Encapsulamiento
- Los objetos `Edge` y `Point3D` siguen siendo inmutables.
- La lógica de "qué devolver" (multiplicación de coordenadas X) está aislada en el punto exacto donde se detecta la convergencia, manteniendo el resto del algoritmo genérico.