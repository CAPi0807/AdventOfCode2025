# Advent of Code - Día 05 (Parte B): Consolidación de Rangos

## 1. El Desafío: El Problema del Solape
En esta fase, no solo validamos IDs, sino que debemos calcular cuántos identificadores únicos existen en una lista masiva de rangos que pueden solaparse o ser adyacentes (ej: `1-5` y `4-10`). Para evitar contar dos veces el mismo ID, hemos implementado un sistema de **fusión de intervalos**.

[cite_start]Este diseño se basa en el fundamento de la **Abstracción**, ocultando la complejidad matemática detrás de una interfaz simple que devuelve el total de IDs disponibles[cite: 75].

## 2. El Dominio: Inteligencia en `NumericRange`
El objeto `NumericRange` ha evolucionado para incluir capacidades de auto-gestión:
- **`overlapsOrTouches`**: Una función lógica que detecta si un rango colisiona con otro o si son correlativos (ej: `[1-5]` toca a `[6-10]`).
- **`merge`**: Un método que genera un **nuevo rango** unificado. [cite_start]Al ser un Record, garantizamos la inmutabilidad: no modificamos los rangos originales, creamos una representación nueva y única[cite: 96].
- **Ordenamiento Natural**: Implementa `Comparable` para permitir que el sistema organice los rangos por su punto de inicio, lo cual es vital para el algoritmo de consolidación.



## 3. El Algoritmo: `RangeMerger`
[cite_start]Esta clase es responsable de la lógica más crítica (Alta Cohesión)[cite: 70]. Utiliza un **Algoritmo de Barrido**:
1. **Ordenación**: Primero ordena los rangos cronológicamente según su inicio.
2. **Consolidación**: Recorre la lista comparando el rango actual con el siguiente. Si se tocan, se fusionan; si no, se guarda el rango consolidado y se inicia uno nuevo.
   [cite_start]Al utilizar `Stream.sorted()`, mantenemos la inmutabilidad de la entrada original, devolviendo una lista totalmente nueva y simplificada[cite: 64].

## 4. El Servicio de Disponibilidad
[cite_start]`AvailabilityService` actúa como un orquestador de bajo acoplamiento[cite: 71].
- [cite_start]**Inyección de Dependencias**: Recibe el `RangeMerger` por constructor, lo que permite que el servicio sea independiente del algoritmo específico de fusión[cite: 91].
- **Cálculo Final**: Una vez consolidados los rangos, simplemente suma el tamaño de cada uno mediante una reducción funcional con Streams. [cite_start]Su única razón de cambiar es si cambia la forma de calcular la disponibilidad global (SRP)[cite: 85].



## 5. Fundamentos de Ingeniería Aplicados
- [cite_start]**Principio Abierto/Cerrado (OCP)**: El sistema está diseñado para que podamos cambiar la forma en que parseamos los datos o cómo los fusionamos sin alterar la lógica de cálculo final[cite: 86, 87].
- [cite_start]**Código Expresivo**: Nombres como `overlapsOrTouches` o `calculateTotalAvailableIDs` permiten que el código documente su propia intención, facilitando el mantenimiento[cite: 74].
- [cite_start]**Modularidad**: Cada componente (Parser, Merger, Service) es independiente y tiene una responsabilidad única y clara[cite: 72].

---
*Este diseño transforma una lista de datos caótica y redundante en un modelo de información consolidado, eficiente y fácil de auditar.*