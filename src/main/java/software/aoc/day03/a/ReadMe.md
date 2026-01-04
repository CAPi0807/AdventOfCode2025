# Advent of Code Day 03

## Principios Implementados

### 1. Single Responsibility Principle (SRP)
- **`Battery`**: Inmutable. Representa el dato.
- **`HighestOrderedPairStrategy`**: Solo contiene el algoritmo matemático de búsqueda de pares.
- **`BatteryParser`**: Solo se encarga de limpiar y transformar Strings.
- **`JoltageService`**: Solo se encarga de aplicar la estrategia a una lista y sumar.

### 2. Open/Closed Principle (OCP)
El sistema usa la interfaz `JoltageStrategy`.
- Si en el futuro el cálculo de "Joltage" cambia (por ejemplo, buscar pares de 3 dígitos, o sumar todos los dígitos), solo necesitamos crear una nueva implementación de `JoltageStrategy` e inyectarla en el `Main`, sin tocar el `Service` ni el `Parser`.

### 3. Inmutabilidad
- Se utiliza un Java Record para `Battery`.
- No existen variables de clase mutables que mantengan estado entre ejecuciones de diferentes líneas.
- El cálculo es puramente funcional (Entrada -> Salida).

### 4. Code Cleanliness
- Se han renombrado métodos y clases con CamelCase.
- Se hace la conversión de caracteres a enteros usando aritmética de caracteres (`c - '0'`) que es estándar y eficiente.