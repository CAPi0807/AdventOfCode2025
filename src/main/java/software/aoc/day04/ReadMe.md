# Advent of Code - Día 04: Seguridad en el Almacén

## 1. Introducción al Diseño
El reto de hoy consiste en identificar "rollos de material" en una cuadrícula que cumplan con ciertos criterios de seguridad basados en su entorno. [cite_start]Hemos diseñado una solución que prioriza el **bajo acoplamiento** [cite: 71] [cite_start]y la **alta cohesión**[cite: 70], separando la gestión de la cuadrícula de las reglas que dictan qué rollos son seleccionables.



## 2. El Modelo de Dominio

### `Grid.java`
[cite_start]Esta clase actúa como nuestra **abstracción** [cite: 75] del espacio físico. Encapsula una matriz de caracteres y proporciona métodos seguros para acceder a la información.
- **Inmutabilidad**: Incluye un constructor privado y un método `without` que permiten crear nuevas versiones de la cuadrícula en lugar de modificar la original, evitando efectos secundarios.
- [cite_start]**Expresividad**: Proporciona un `Stream` de todas las posiciones, facilitando el procesamiento funcional del almacén[cite: 74].

### `Position.java` (Record)
En lugar de manejar enteros `x` e `y` sueltos por el código, usamos este `record` inmutable. Su método más potente es `neighbors()`, que utiliza un sistema de **deltas** para calcular automáticamente las 8 celdas adyacentes, encapsulando la complejidad matemática de la vecindad.

## 3. Abstracción de Reglas: `SelectionRule`
[cite_start]Para cumplir con el **Principio de Inversión de Dependencias (DIP)**[cite: 91], hemos creado una interfaz funcional. Esto significa que nuestro servicio no sabe *qué* está buscando, solo sabe *cómo preguntar* si una posición es válida.

- **`RollSelectionRule`**: Es nuestra implementación concreta. Aplica la regla de negocio: un símbolo debe ser `@` y debe tener menos de 4 vecinos del mismo tipo. [cite_start]Es una clase enfocada exclusivamente en esta tarea, respetando el **Principio de Responsabilidad Única (SRP)**[cite: 85].

## 4. El Servicio: `WarehouseService`
El servicio funciona como un motor de procesamiento **stateless**.
1. Genera un flujo de todas las coordenadas posibles del almacén.
2. [cite_start]Filtra cada posición utilizando la regla inyectada[cite: 91].
3. Cuenta los resultados.
   [cite_start]Al recibir la regla por constructor, el servicio está **abierto a la extensión pero cerrado a la modificación (OCP)**[cite: 86, 87].



## 5. Fundamentos de Ingeniería Aplicados
- [cite_start]**Modularidad**: Hemos dividido el sistema en componentes independientes que podrían ser probados por separado (Unit Testing).
- [cite_start]**Código Expresivo**: El uso de Java Streams y nombres de métodos claros (como `isValid` o `matches`) asegura que el código sea fácil de leer y mantener[cite: 74].
- [cite_start]**Evitar Repetición (DRY)**: La lógica de navegación por la matriz está centralizada en `Grid` y `Position`, eliminando la necesidad de escribir bucles anidados `for` en múltiples lugares del programa[cite: 96].

---
*Este enfoque garantiza que, si las reglas de seguridad del almacén cambian en la Parte B, el núcleo de nuestro sistema permanecerá estable y robusto.*
---

# Parte B: Simulación de Estabilidad

## 1. Introducción: Uno a muchos
Mientras que la Parte A era un recuento estático, la Parte B introduce un comportamiento dinámico: la **reacción en cadena**. Cuando un rollo inestable se elimina, puede provocar que sus vecinos también pierdan estabilidad, requiriendo un proceso iterativo hasta que el sistema se equilibre.

[cite_start]Esta solución se basa en fundamentos de **alta cohesión** y **bajo acoplamiento**, reutilizando la infraestructura de coordenadas del reto anterior para centrarse puramente en la lógica de simulación[cite: 70, 71].

## 2. El Motor de Simulación: `WarehouseSimulator`
[cite_start]El simulador actúa como un motor de ejecución que no conoce las reglas específicas de eliminación, solo conoce el proceso de iteración[cite: 110, 119].
- [cite_start]**Bucle de Estabilidad**: Ejecuta un ciclo `while` que solo se detiene cuando la regla de selección no devuelve más elementos para eliminar[cite: 108].
- [cite_start]**Transición de Estado Inmutable**: En cada paso, se genera una versión nueva del almacén (`currentGrid.without()`)[cite: 114]. Esto garantiza que los cambios de una iteración no interfieran con el análisis de la misma, manteniendo la integridad de los datos.



## 3. Nueva Abstracción de Reglas: `SelectionRule`
[cite_start]Hemos evolucionado la interfaz del día anterior para manejar conjuntos de datos en lugar de posiciones individuales[cite: 111]:
- [cite_start]**`findMatches`**: A diferencia de la Parte A (que validaba una posición), esta nueva interfaz analiza el almacén completo y devuelve una lista de todas las posiciones que incumplen la norma[cite: 89, 112].
- **`UnstableRollRule`**: Implementa el criterio de inestabilidad. Si un rollo tiene menos de 4 vecinos, se marca para su eliminación. [cite_start]Al estar separada del simulador, podríamos cambiar la regla por una de "incendio" o "caducidad" sin tocar el motor de simulación[cite: 85, 86].

## 4. Reutilización y Extensibilidad
[cite_start]Este reto es un caso de éxito del principio **Open/Closed** (OCP)[cite: 86]:
- [cite_start]**Reutilización de A**: Importamos `Grid` y `Position` del paquete original. No hemos tenido que volver a escribir la compleja lógica de cálculo de vecinos o límites de la matriz.
- [cite_start]**Acoplamiento Débil**: El `Main` simplemente conecta las piezas[cite: 91]. [cite_start]Inyecta la regla de inestabilidad en el simulador y le entrega el grid inicial, siguiendo el patrón de diseño de **Inversión de Dependencias**[cite: 91, 106].



## 5. Resultados y Salida de Datos
La simulación concluye devolviendo un `SimulationResult`. Este objeto contiene:
1. El número total de rollos que fueron retirados durante todo el proceso.
2. El estado final del almacén para su visualización.
   [cite_start]Este diseño permite que el `Main` sea extremadamente limpio y expresivo, delegando toda la complejidad técnica a los componentes especializados[cite: 74, 85].

---
*Al tratar las reglas de negocio como piezas intercambiables y el estado como algo inmutable, hemos creado un simulador robusto capaz de manejar cualquier lógica de evolución de celdas.*