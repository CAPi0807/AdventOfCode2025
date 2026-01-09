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