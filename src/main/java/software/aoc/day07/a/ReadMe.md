# Advent of Code - Día 07: Simulación de Rayos y Gravedad

## 1. Introducción al Diseño
El reto de hoy consiste en seguir la trayectoria de un rayo que cae a través de un mapa, pudiendo dividirse al impactar con ciertos elementos (`^`). Hemos aplicado un diseño de **Alta Cohesión** donde la simulación no conoce las reglas físicas, sino que las consulta a un motor especializado. [cite: 70]



## 2. El Escenario: `Grid`
La clase `Grid` actúa como el fundamento del sistema, gestionando el mapa de forma inmutable.
- **Búsqueda Dinámica**: Localiza automáticamente el punto de origen `S` sin necesidad de coordenadas fijas en el código.
- **Seguridad de Límites**: Centraliza la validación en `isOutOfBounds`, asegurando que la simulación nunca intente acceder a memoria fuera de los límites de la matriz. [cite: 94]

## 3. El Motor de Física: `RayPhysics`
Siguiendo el **Principio de Responsabilidad Única (SRP)**, la lógica de colisión está aislada de la lógica de movimiento.
- **`InteractionResult`**: Es un **Value Object** (Record) que comunica si el rayo debe dividirse y hacia qué columnas debe propagarse.
- **Abstracción de Interacción**: Al separar esta lógica, el sistema cumple con el **Principio Abierto/Cerrado (OCP)**; añadir espejos o bloqueos es tan sencillo como actualizar este componente sin tocar el simulador. [cite: 86]

## 4. El Simulador de Rayos
`BeamSimulator` realiza una propagación por niveles (fila a fila), similar a un recorrido en anchura.
- **Evolución de Estado**: Utiliza un conjunto de `activeColumns` que se reemplaza en cada fila, evitando efectos secundarios y manteniendo la **Inmutabilidad** del proceso. [cite: 110]
- **Detección de Divisiones**: Lleva el recuento de "splits" basándose exclusivamente en lo que dicta la física, actuando como un orquestador puro.
- **Optimización de Salida**: Si en una fila no quedan rayos activos (se salieron del mapa o se bloquearon), el simulador termina la ejecución inmediatamente.



## 5. Fundamentos de Software Aplicados
- **Inversión de Dependencias (DIP)**: El simulador depende de una lógica de física inyectada, facilitando el intercambio de reglas.
- **Bajo Acoplamiento**: Los componentes se comunican mediante contratos simples (`InteractionResult`), lo que permite escalar el sistema con nuevos tipos de obstáculos sin riesgo de regresiones. [cite: 71, 112]
- **Código Expresivo**: El uso de métodos estáticos como `InteractionResult.split()` mejora la legibilidad, haciendo que el código "se explique a sí mismo". [cite: 74, 106]

---
*Este enfoque transforma un problema de trayectorias en una simulación física modular, preparada para cualquier tipo de obstáculo que el reto pueda presentar en el futuro.*