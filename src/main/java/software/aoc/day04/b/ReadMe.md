# Advent of Code Day 04 (Parte B)

La simulación de eliminación de rollos introduce el concepto de estado evolutivo.

## Diseño Implementado

### Grid Inmutable (`Grid`)
Para cumplir con el requisito de inmutabilidad y evitar errores comunes en simulaciones donde se actualiza el tablero mientras se lee:
- La clase `Grid` es inmutable.
- El método `without(List<Position>)` **devuelve una nueva instancia** de `Grid` con las celdas especificadas vaciadas.
- Esto asegura que cada paso de la simulación es atómico y el estado anterior no se corrompe.

### Servicio de Simulación (`WarehouseSimulator`)
Encapsula el bucle `while(true)`.
1. Interroga al grid actual usando la regla inyectada (`UnstableRollRule`).
2. Si hay coincidencias, crea un nuevo grid y acumula el contador.
3. Si no hay coincidencias, termina y devuelve un `SimulationResult`.

### Single Responsibility Principle
- `UnstableRollRule`: Solo sabe decidir quién debe morir en un instante dado.
- `WarehouseSimulator`: Solo sabe repetir el proceso hasta la estabilidad.
- `Grid`: Solo sabe almacenar datos y crear copias modificadas.