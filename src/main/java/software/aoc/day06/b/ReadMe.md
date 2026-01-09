# Advent of Code - Día 06 (Parte B): Escaneo Vertical y Bloques Dinámicos

## 1. Introducción: El Desafío del Barrido Vertical (0:00 - 1:00)
En esta fase, la estructura del problema cambia radicalmente. Los números ya no están en celdas individuales, sino que se forman verticalmente concatenando dígitos de varias filas. Además, estos números se agrupan en "bloques" separados por columnas vacías. Para resolver esto, hemos implementado un **GridScanner** que realiza un barrido columna a columna para reconstruir el dominio.



## 2. El Motor de Segmentación: `GridScanner` (1:00 - 2:30)
[cite_start]Esta clase es el núcleo de la infraestructura y refleja una alta **Cohesión**[cite: 70]:
- [cite_start]**Extracción de Columnas**: Mediante `extractColumn`, el sistema mira hacia abajo en cada índice, recolectando dígitos para formar números largos y detectando el operador asociado[cite: 75].
- **Detección de Bloques**: El escáner mantiene un estado temporal. Cuando detecta una columna vacía, "corta" el bloque actual, lo empaqueta en un `NumberBlock` y reinicia el proceso.
- **Robustez**: Maneja longitudes de fila desiguales y huecos, asegurando que solo los caracteres válidos se conviertan en datos de negocio.

## 3. Lógica de Negocio en `Operation` (2:30 - 3:30)
[cite_start]Siguiendo el **Principio de Inversión de Dependencias (DIP)**, la lógica de decisión se ha movido a una abstracción:
- **Firma de Bloque**: El sistema recolecta todos los operadores vistos bajo un bloque. El método `determineFromSignature` aplica la regla crítica: si existe al menos un `*`, la operación es multiplicación; de lo contrario, es suma.
- [cite_start]**Estrategias Funcionales**: Al usar `LongBinaryOperator`, el Enum no solo nombra la operación, sino que la ejecuta, facilitando el uso de Streams para la reducción final[cite: 74].

## 4. Inmutabilidad y Seguridad (3:30 - 4:15)
[cite_start]Para garantizar la calidad y mantenibilidad del sistema[cite: 58]:
- [cite_start]**Records e Inmutabilidad**: `NumberBlock` utiliza la característica de Record de Java y aplica una **copia defensiva** (`List.copyOf`) en su constructor. [cite_start]Esto asegura que una vez que el bloque sale del escáner, nadie pueda alterar sus números[cite: 96].
- **Manejo de Nulos**: Se utiliza `Optional<Long>` para representar la posibilidad de que una columna no contenga un número, eliminando el riesgo de `NullPointerException` durante el barrido vertical.



## 5. Fundamentos de Software Aplicados (4:15 - 5:00)
- [cite_start]**Responsabilidad Única (SRP)**: El `ProblemSolver` se limita a coordinar, mientras que el `GridScanner` resuelve la complejidad del formato físico.
- [cite_start]**Abierto/Cerrado (OCP)**: Si el reto añadiera nuevos operadores o reglas de prioridad, solo necesitaríamos extender el Enum `Operation` sin modificar el motor de escaneo[cite: 86].
- [cite_start]**Bajo Acoplamiento**: Los componentes se comunican a través de Records simples, lo que permite cambiar la lógica del escáner (ej: para leer en diagonal) sin afectar al cálculo de los bloques[cite: 71].

---
*Esta arquitectura convierte un problema de parsing visual complejo en un flujo de datos estructurado, inmutable y fácil de escalar.*