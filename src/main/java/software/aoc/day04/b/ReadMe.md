# Advent of Code - Día 04 (Parte B): Simulación de Estabilidad

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