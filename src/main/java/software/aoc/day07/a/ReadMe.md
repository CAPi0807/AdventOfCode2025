# Advent of Code Day 07

## Principios Implementados

### 1. Single Responsibility Principle (SRP)
- **`Grid`**: Responsable únicamente de almacenar el mapa y validar coordenadas. No sabe nada de rayos ni movimientos.
- **`RayPhysics`**: Responsable de las reglas de interacción (¿Qué pasa si toco una 'S' o un '^'?). Devuelve un objeto de valor con la consecuencia.
- **`BeamSimulator`**: Responsable de orquestar la simulación fila a fila y llevar la cuenta.
- **`InteractionResult`**: Value Object (DTO) para comunicar la física con el simulador.

### 2. Open/Closed Principle (OCP)
- La lógica de interacción está aislada en `RayPhysics`.
- Si se añaden nuevos obstáculos (ej: `#` bloquea el rayo, `\` desvía a la derecha), solo hay que modificar `RayPhysics` o extenderlo, sin tocar el bucle principal de simulación.

### 3. Inmutabilidad
- `InteractionResult` es un Java Record.
- `Grid` es inmutable.
- En `BeamSimulator`, el conjunto de columnas activas (`activeColumns`) se reemplaza completamente en cada iteración (`nextColumns`), evitando la mutación de estado compartido complejo.

### 4. Robustez
- Validación de límites centralizada en `Grid.isOutOfBounds`.
- Manejo seguro de la posición inicial 'S'.