# Advent of Code Day 06

## Principios Implementados

### 1. Open/Closed Principle (OCP)
La lógica de las operaciones matemáticas se ha extraído al Enum `Operator`.
- El servicio de cálculo `ColumnCalculationService` **no conoce** los operadores específicos (no hay `if (op == '*')`). Simplemente llama a `op.apply()`.
- Si se quisiera añadir una resta (`-`), solo se añade una entrada al Enum `Operator` con su lógica, sin tocar el parser (si se añade el char) ni el calculador.

### 2. Single Responsibility Principle (SRP)
- **`ProblemParser`**: Analiza el texto y busca la línea de operadores.
- **`Matrix`**: Mantiene los datos numéricos de forma segura.
- **`ProblemSchema`**: Valida que la matriz y los operadores sean compatibles (mismo ancho).
- **`ColumnCalculationService`**: Itera y acumula resultados.

### 3. Inmutabilidad y Type Safety
- Uso de **Java Records** (`Matrix`, `ProblemSchema`) para estructuras de datos inmutables.
- Validación fuerte en el constructor de `ProblemSchema`: Es imposible crear un objeto de problema inválido donde el número de columnas no coincida con los operadores.