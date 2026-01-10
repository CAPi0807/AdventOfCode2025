# Advent of Code Day 03

## Principios Implementados

### 1. Single Responsibility Principle (SRP)
- **`Battery`**: Inmutable. Representa el dato.
- **`HighestOrderedPairStrategy`**: Solo contiene el algoritmo matemático de búsqueda de pares.
- **`BatteryParser`**: Solo se encarga de limpiar y transformar Strings.
- **`JoltageService`**: Solo se encarga de aplicar la estrategia a una lista y sumar.

### 2. Open/Closed Principle (OCP)
El sistema usa la interfaz `JoltageStrategy`.
- Si en el futuro el cálculo de "Joltage" cambia (por ejemplo, buscar pares de 3 dígitos, o sumar todos los dígitos), solo necesitamos crear una nueva implementación de `JoltageStrategy` e inyectarla en el `Main`, sin tocar el `Service` ni el `Parser`.

### 3. Inmutabilidad
- Se utiliza un Java Record para `Battery`.
- No existen variables de clase mutables que mantengan estado entre ejecuciones de diferentes líneas.
- El cálculo es puramente funcional (Entrada -> Salida).

### 4. Code Cleanliness
- Se han renombrado métodos y clases con CamelCase.
- Se hace la conversión de caracteres a enteros usando aritmética de caracteres (`c - '0'`) que es estándar y eficiente.
---

# Parte B: Algoritmos Voraces y Escalabilidad

## 1. Introducción al Desafío
En esta segunda parte, el objetivo evoluciona de encontrar un par de dígitos a construir el número de 12 cifras más grande posible a partir de la secuencia de la batería. Para lograrlo, hemos implementado una **estrategia de selección voraz (Greedy)**.

[cite_start]El diseño destaca por su **bajo acoplamiento** [cite: 71][cite_start], ya que el servicio encargado de procesar las baterías no ha necesitado cambios; simplemente hemos "inyectado" una nueva lógica de cálculo[cite: 91].

## 2. El Corazón del Algoritmo: `GreedySelectionStrategy`
[cite_start]Este componente es una implementación de la interfaz `JoltageStrategy`. Su lógica se basa en la toma de decisiones óptimas locales para alcanzar un máximo global:

- **Ventana de Búsqueda Dinámica**: El algoritmo calcula un `searchLimit` en cada iteración. [cite_start]Esto garantiza que, aunque busquemos el dígito más alto, siempre dejemos caracteres suficientes a la derecha para completar las 12 posiciones requeridas[cite: 70].
- [cite_start]**Búsqueda Voraz**: Dentro de cada ventana, el código rastrea el carácter con el valor ASCII más alto[cite: 74].
- **Optimización de Salida Temprana**: Si el algoritmo encuentra un '9', deja de buscar en la ventana actual inmediatamente, ya que no existe un dígito superior en base 10.



## 3. Reutilización e Inversión de Control
[cite_start]Gracias a que nuestro sistema depende de **abstracciones** en lugar de clases concretas[cite: 91], la integración de la Parte B ha sido inmediata:
- [cite_start]Utilizamos el `BatteryParser` original para transformar el texto[cite: 96].
- [cite_start]El `JoltageService` sigue siendo el orquestador que mapea y suma los resultados.
- [cite_start]La entidad `Battery` permanece inmutable, sirviendo como un contenedor de datos fiable y sin efectos secundarios[cite: 64, 74].

## 4. Evolución Técnica y Tipado
Un aspecto crítico en esta fase ha sido el manejo de la **capacidad de datos**:
- **De `int` a `long`**: Dado que el resultado es un número de 12 dígitos, sobrepasamos los 2.147 millones que permite un entero estándar. [cite_start]El uso de `long` asegura la precisión y evita errores de desbordamiento[cite: 74].
- [cite_start]**Eficiencia en Strings**: Se utiliza `StringBuilder` para construir el número final antes de su conversión, optimizando el uso de memoria en comparación con la concatenación repetida de Strings[cite: 74].



## 5. Reflexión sobre Principios de Diseño
- [cite_start]**Abierto/Cerrado (OCP)**: Hemos extendido la funcionalidad del software añadiendo una clase, no modificando las existentes[cite: 86, 87].
- **Responsabilidad Única (SRP)**: La lógica del algoritmo voraz está aislada. [cite_start]Si las reglas de selección cambian mañana (por ejemplo, a 15 dígitos), solo se verá afectada esta estrategia.
- [cite_start]**Modularidad**: El sistema se comporta como un conjunto de piezas intercambiables, donde el `Main` actúa como el punto de montaje.

---
*Este diseño no solo resuelve el problema actual, sino que establece una infraestructura donde nuevos algoritmos de análisis de energía pueden integrarse en segundos.*