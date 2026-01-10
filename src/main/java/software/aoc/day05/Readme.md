# Advent of Code - Día 05: Validador de Productos por Rangos

## 1. Introducción al Diseño
El reto de hoy implica procesar una lista de identificadores frente a un conjunto de rangos permitidos. Hemos aplicado un diseño que prioriza la **Alta Cohesión**, asegurando que cada componente se enfoque en una única tarea del proceso: interpretar el archivo, definir la regla de validez o ejecutar el conteo final.



## 2. Parsing de Estructura Híbrida
El archivo de entrada es inusual porque contiene dos secciones distintas separadas por una línea vacía.
- **`InputParser`**: Actúa como un traductor que entiende esta estructura. Identifica el punto de ruptura (línea vacía) y divide el trabajo.
- **`InputData`**: Los resultados se empaquetan en este Record inmutable, garantizando que los datos no sufran cambios inesperados una vez procesados.
- **`NumericRange.parse()`**: Utilizamos un **Factory Method** estático dentro del Record para encapsular la creación del objeto a partir de una cadena de texto, manteniendo el constructor limpio y enfocado en la validación de datos.

## 3. Política de Validación Intercambiable
Para cumplir con el **Principio Abierto/Cerrado (OCP)**, la validación se define mediante la interfaz `ValidationPolicy`.
- **`AllowedRangesPolicy`**: Esta clase implementa la lógica de "permitido si está en algún rango".
- **Copia Defensiva**: El constructor utiliza `List.copyOf` para proteger su estado interno. Esto evita que cambios externos en la lista original afecten la integridad de la política una vez creada.
- **Lógica Declarativa**: Mediante Java Streams y `anyMatch`, la validación es altamente expresiva y eficiente.

## 4. El Servicio: `ProductService`
El servicio es un componente de alto nivel que demuestra el **Principio de Inversión de Dependencias (DIP)**.
- No depende de rangos específicos ni de reglas rígidas; depende de la abstracción `ValidationPolicy`.
- Su única función es filtrar el flujo de IDs y contar cuántos cumplen con "la política" que se le entregue. Esto permite que el servicio sea reutilizable para cualquier tipo de validación futura.



## 5. Fundamentos de Calidad Aplicados
- **Inmutabilidad**: El uso extensivo de **Records** garantiza que los datos sean representaciones únicas e inequívocas (Principio DRY).
- **Código Expresivo**: El sistema se lee como una serie de reglas de negocio en lugar de bucles técnicos complejos, facilitando el mantenimiento a largo plazo.
- **Bajo Acoplamiento**: El `Main` es el único lugar donde las clases concretas se conocen, actuando como el "armario en la entrada" que organiza el sistema antes de que empiece a funcionar.

---
*Este diseño transforma un problema de filtrado de datos en una arquitectura flexible, capaz de adaptarse a nuevas reglas de validación simplemente inyectando una nueva política.*
---

# Parte B: Consolidación de Rangos

## 1. El Desafío: El Problema del Solape
En esta fase, no solo validamos IDs, sino que debemos calcular cuántos identificadores únicos existen en una lista masiva de rangos que pueden solaparse o ser adyacentes (ej: `1-5` y `4-10`). Para evitar contar dos veces el mismo ID, hemos implementado un sistema de **fusión de intervalos**.

Este diseño se basa en el fundamento de la **Abstracción**, ocultando la complejidad matemática detrás de una interfaz simple que devuelve el total de IDs disponibles.

## 2. El Dominio: Inteligencia en `NumericRange`
El objeto `NumericRange` ha evolucionado para incluir capacidades de auto-gestión:
- **`overlapsOrTouches`**: Una función lógica que detecta si un rango colisiona con otro o si son correlativos (ej: `[1-5]` toca a `[6-10]`).
- **`merge`**: Un método que genera un **nuevo rango** unificado. Al ser un Record, garantizamos la inmutabilidad: no modificamos los rangos originales, creamos una representación nueva y única[cite: 96].
- **Ordenamiento Natural**: Implementa `Comparable` para permitir que el sistema organice los rangos por su punto de inicio, lo cual es vital para el algoritmo de consolidación.



## 3. El Algoritmo: `RangeMerger`
Esta clase es responsable de la lógica más crítica (Alta Cohesión). Utiliza un **Algoritmo de Barrido**:
1. **Ordenación**: Primero ordena los rangos cronológicamente según su inicio.
2. **Consolidación**: Recorre la lista comparando el rango actual con el siguiente. Si se tocan, se fusionan; si no, se guarda el rango consolidado y se inicia uno nuevo.
   Al utilizar `Stream.sorted()`, mantenemos la inmutabilidad de la entrada original, devolviendo una lista totalmente nueva y simplificada.

## 4. El Servicio de Disponibilidad
`AvailabilityService` actúa como un orquestador de bajo acoplamiento
- **Inyección de Dependencias**: Recibe el `RangeMerger` por constructor, lo que permite que el servicio sea independiente del algoritmo específico de fusión.
- **Cálculo Final**: Una vez consolidados los rangos, simplemente suma el tamaño de cada uno mediante una reducción funcional con Streams. Su única razón de cambiar es si cambia la forma de calcular la disponibilidad global (SRP).



## 5. Fundamentos de Ingeniería Aplicados
- **Principio Abierto/Cerrado (OCP)**: El sistema está diseñado para que podamos cambiar la forma en que parseamos los datos o cómo los fusionamos sin alterar la lógica de cálculo final.
- **Código Expresivo**: Nombres como `overlapsOrTouches` o `calculateTotalAvailableIDs` permiten que el código documente su propia intención, facilitando el mantenimiento.
- **Modularidad**: Cada componente (Parser, Merger, Service) es independiente y tiene una responsabilidad única y clara.

---
*Este diseño transforma una lista de datos caótica y redundante en un modelo de información consolidado, eficiente y fácil de auditar.*