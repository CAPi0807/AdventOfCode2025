# Advent of Code Day 07 (Parte B)

La Parte B requiere calcular la multiplicidad de caminos, lo cual es un problema de estado acumulativo.

## Diseño Implementado

### 1. Estado Inmutable (`SimulationState`)

- `SimulationState` es una clase inmutable que guarda el mapa de `Columna -> CantidadCaminos`.
- Su método `evolve()` toma el Grid y las reglas, y produce una **nueva instancia** de `SimulationState` para la siguiente fila.
- Esto encapsula la lógica "delicada" de fusión (`Map.merge(key, value, Long::sum)`) dentro de un método de dominio claro.

### 2. Separación Física/Lógica
- **`InteractionRule`**: Define *cómo* se mueven las cosas (Topología: offsets).
- **`SimulationState`**: Define *cuántas* cosas se mueven (Aritmética: sumas).

### 3. Principio DRY (Don't Repeat Yourself)
- Se reutiliza la clase `Grid` de la parte A (si estuvieran en el mismo paquete) o se duplica limpiamente en el paquete `b` sin dependencias cruzadas sucias.
- La lógica de límites (`isOutOfBounds`) protege el cálculo de caminos.