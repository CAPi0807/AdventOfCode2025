# Advent of Code - Día 07 (Parte B): Cálculo de Caminos y Convergencia

## 1. Introducción: De Partículas a Probabilidades
En esta segunda fase, el reto no es solo seguir la trayectoria, sino calcular cuántos caminos diferentes pueden alcanzar la base del mapa. Esto transforma el problema en uno de **programación dinámica** fila a fila. En lugar de simular rayos individuales, simulamos un "frente de onda" que se acumula cuando múltiples trayectorias convergen en la misma posición.



## 2. Gestión de Estado Inmutable: `SimulationState`
Siguiendo los fundamentos de **Inmutabilidad** y **Alta Cohesión**, hemos creado una clase dedicada a gestionar el estado actual de la simulación.
- **Encapsulamiento de Datos**: Utiliza un mapa interno donde la clave es la columna y el valor es la cantidad de caminos (Long) que llegan a ella.
- **Copia Defensiva**: El constructor utiliza `Map.copyOf` para asegurar que el estado de una fila sea inmutable y no pueda ser alterado por agentes externos.
- **Lógica de Fusión**: El método `evolve` utiliza `Map.merge` con una función de suma. Este es el corazón del sistema: si dos caminos de diferentes columnas convergen en una misma celda inferior, sus intensidades se suman.

## 3. Topología de Movimiento: `InteractionRule`
Para mantener el **Bajo Acoplamiento**, hemos separado la aritmética de los caminos de la geometría del movimiento.
- **Offsets de Dirección**: Esta clase solo se preocupa de dictar hacia dónde se mueve el flujo. Ante un divisor (`^`), devuelve una lista con los desplazamientos `[-1, 1]`.
- **Simplificación**: Al devolver una lista de desplazamientos, permite que el simulador trate de forma uniforme tanto los pasos directos como las divisiones, eliminando la necesidad de sentencias `if` complejas en el motor principal (Principio de Responsabilidad Única).

## 4. Orquestación: `PathCalculatorService`
El servicio de cálculo actúa como el director de orquesta que hace evolucionar el sistema.
- **Inyección de Dependencias (DIP)**: Recibe las reglas físicas por constructor, permitiendo que el cálculo sea agnóstico a si el rayo se divide, rebota o se bloquea.
- **Bucle de Evolución**: Itera a través de la altura del `Grid`, actualizando el `SimulationState` en cada paso. Si en algún punto el estado se queda vacío (todos los caminos se salieron del mapa), el servicio detiene la ejecución prematuramente (Short-circuit).



## 5. Fundamentos SOLID Aplicados
- **Principio Abierto/Cerrado (OCP)**: Si el reto introdujera un "agujero negro" que absorbe caminos, solo tendríamos que crear una regla que devuelva una lista vacía de offsets; el resto del código permanecería intacto.
- **Código Expresivo**: El uso de un **Factory Method** como `SimulationState.initial()` hace que el punto de partida de la simulación sea explícito y fácil de entender.
- **Modularidad**: Cada componente tiene una frontera clara. El `Grid` maneja el espacio físico, la `InteractionRule` maneja la física y el `SimulationState` maneja la matemática de los caminos.

---
*Este diseño permite procesar millones de caminos potenciales de forma eficiente, utilizando la convergencia para evitar el crecimiento exponencial del cálculo.*