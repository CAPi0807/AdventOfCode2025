# Advent of Code Day 02

## Principios Aplicados

### 1. Single Responsibility Principle (SRP)
El `Main` está descentralizado:
- **`RangeParser`**: Único responsable de interpretar el formato del texto ("1-10,20-30").
- **`NumericRange`**: Record inmutable que gestiona los límites del rango y la generación del Stream.
- **`RepetitiveNumberPredicate`**: Contiene exclusivamente la lógica para decidir si un número cumple la condición de "repetitivo".
- **`RangeSumService`**: Orquesta la lógica de negocio (expandir rangos, filtrar, sumar).

### 2. Open/Closed Principle (OCP)
El sistema está diseñado para soportar nuevos tipos de filtros numéricos sin modificar el código existente.
- Se introdujo la interfaz `NumberPredicate` que haría el problema expansible a ottros filtros si fuera necesario.


### 3. Inmutabilidad
- `NumericRange` es un Java Record.
- No hay estado compartido mutable en el servicio.
- El uso de `LongStream` favorece un enfoque funcional y declarativo sobre bucles imperativos.

### 4. Eficiencia (Uso de Streams)
A diferencia del problema del "Dial" (que requería acumulación de estado paso a paso), este problema es **Stateless** (evaluar un número no depende del anterior). Por ello, el uso de `flatMapToLong` y `filter` es la solución más óptima y legible, aprovechando la API de Streams de Java para manejar grandes volúmenes de datos.