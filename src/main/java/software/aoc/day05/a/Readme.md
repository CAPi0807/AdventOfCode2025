# Advent of Code Day 05

## Principios Implementados

### 1. Separation of Concerns (SoC) en el Parsing
El archivo de entrada tiene dos estructuras diferentes (Rangos y Lista de IDs) separadas por una línea vacía.
- **`InputParser`**: Se encarga exclusivamente de entender esta estructura de archivo, dividirla y delegar el parsing de cada línea a los objetos correspondientes (`NumericRange` o `Long`).
- Devuelve un **DTO** inmutable (`InputData`) que contiene las dos listas limpias.

### 2. Open/Closed Principle (OCP)
- Uso la interfaz `ValidationPolicy`.
- `AllowedRangesPolicy` es una implementación específica que valida contra una lista de rangos.
- Si mañana la regla de validación cambia, se puede implementar una nueva política sin tocar el `ProductService`.

### 3. Inmutabilidad
- `NumericRange` e `InputData` son Java Records, solo dan info, no se actualizan.
- `AllowedRangesPolicy` realiza una copia defensiva (`List.copyOf`) de la lista de rangos en su constructor para evitar modificaciones externas posteriores.
- No hay métodos `loadRanges` que muten el estado de un objeto existente; todo se pasa en el constructor.

### 4. Single Responsibility Principle (SRP)
- **`ProductService`**: Solo sabe contar cuántos IDs pasan un filtro.
- **`AllowedRangesPolicy`**: Solo sabe decidir si UN id es válido.
- **`Main`**: Solo hace el wiring de mis componentes.