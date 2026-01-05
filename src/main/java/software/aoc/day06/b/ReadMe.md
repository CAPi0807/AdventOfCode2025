# Advent of Code Day 06 (Parte B)

Esta parte introduce una complejidad de parsing ortogonal (lectura vertical y agrupación dinámica).

## Principios Implementados

### 1. Single Responsibility Principle (SRP)
- **`GridScanner`**: Es la clase más importante. Su única responsabilidad es transformar una matriz de caracteres (Strings) en una lista de bloques lógicos (`NumberBlock`). Maneja la complejidad de iterar columnas, detectar dígitos verticales y decidir cuándo se corta un bloque por una columna vacía.
- **`ProblemSolver`**: Orquesta la entrada y salida, pero delega el "trabajo sucio" al scanner.

### 2. Strategy Pattern & OCP (`Operation`)
El Enum `Operation` define las estrategias (SUMA, MULTIPLICACION).
- Incluye el método estático `determineFromSignature` que encapsula la regla de negocio: *"Si aparece un asterisco en cualquier lugar debajo del bloque, es multiplicación"*.
- Si esta regla cambia, solo se modifica este Enum.

### 3. Inmutabilidad
- `ParsedColumn` y `NumberBlock` son Records.
- Las listas dentro de `NumberBlock` son copias defensivas inmutables (`List.copyOf`), previniendo errores si el Scanner reutilizara sus buffers internos (algo común en parsing).

### 4. Robustez Lógica
La lógica de lectura vertical (`extractColumn`) concatena caracteres de múltiples filas para formar un solo número, manejando huecos verticales correctamente gracias a `Character.isDigit`.