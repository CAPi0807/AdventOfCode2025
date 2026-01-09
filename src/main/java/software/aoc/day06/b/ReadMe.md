# Advent of Code - Día 06 (Parte B): Escaneo Vertical y Bloques Dinámicos

## 1. Introducción: El Desafío del Barrido Vertical
En esta fase, la estructura del problema cambia radicalmente. Los números ya no están en celdas individuales, sino que se forman verticalmente concatenando dígitos de varias filas. Además, estos números se agrupan en "bloques" separados por columnas vacías. Para resolver esto, hemos implementado un **GridScanner** que realiza un barrido columna a columna para reconstruir el dominio.



## 2. El Motor de Segmentación: `GridScanner`
Esta clase es el núcleo de la infraestructura y refleja una alta **Cohesión**:
- **Extracción de Columnas**: Mediante `extractColumn`, el sistema mira hacia abajo en cada índice, recolectando dígitos para formar números largos y detectando el operador asociado.
- **Detección de Bloques**: El escáner mantiene un estado temporal. Cuando detecta una columna vacía, "corta" el bloque actual, lo empaqueta en un `NumberBlock` y reinicia el proceso.
- **Robustez**: Maneja longitudes de fila desiguales y huecos, asegurando que solo los caracteres válidos se conviertan en datos de negocio.

## 3. Lógica de Negocio en `Operation
Siguiendo el **Principio de Inversión de Dependencias (DIP)**, la lógica de decisión se ha movido a una abstracción:
- **Firma de Bloque**: El sistema recolecta todos los operadores vistos bajo un bloque. El método `determineFromSignature` aplica la regla crítica: si existe al menos un `*`, la operación es multiplicación; de lo contrario, es suma.
- **Estrategias Funcionales**: Al usar `LongBinaryOperator`, el Enum no solo nombra la operación, sino que la ejecuta, facilitando el uso de Streams para la reducción final.

## 4. Inmutabilidad y Seguridad
Para garantizar la calidad y mantenibilidad del sistema:
- **Records e Inmutabilidad**: `NumberBlock` utiliza la característica de Record de Java y aplica una **copia defensiva** (`List.copyOf`) en su constructor. Esto asegura que una vez que el bloque sale del escáner, nadie pueda alterar sus números.
- **Manejo de Nulos**: Se utiliza `Optional<Long>` para representar la posibilidad de que una columna no contenga un número, eliminando el riesgo de `NullPointerException` durante el barrido vertical.



## 5. Fundamentos de Software Aplicados
- **Responsabilidad Única (SRP)**: El `ProblemSolver` se limita a coordinar, mientras que el `GridScanner` resuelve la complejidad del formato físico.
- **Abierto/Cerrado (OCP)**: Si el reto añadiera nuevos operadores o reglas de prioridad, solo necesitaríamos extender el Enum `Operation` sin modificar el motor de escaneo.
- **Bajo Acoplamiento**: Los componentes se comunican a través de Records simples, lo que permite cambiar la lógica del escáner (ej: para leer en diagonal) sin afectar al cálculo de los bloques.

---
*Esta arquitectura convierte un problema de parsing visual complejo en un flujo de datos estructurado, inmutable y fácil de escalar.*