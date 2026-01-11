# Advent of Code Day 03

## Principios Implementados

### 1. Single Responsibility Principle (SRP)
- **`Battery`**: Inmutable. Representa el dato.
- **`HighestOrderedPairStrategy`**: Solo contiene el algoritmo matemático de búsqueda de pares.
- **`BatteryParser`**: Solo se encarga de limpiar y transformar Strings.
- **`JoltageService`**: Solo se encarga de aplicar la estrategia a una lista y sumar.

### 2. Open/Closed Principle (OCP)
El sistema usa la interfaz `JoltageStrategy`.
- Si en el futuro el cálculo de "Joltage" cambia, solo necesitamos crear una nueva implementación de `JoltageStrategy` e inyectarla en el `Main`, sin tocar el `Service` ni el `Parser`.

### 3. Inmutabilidad
- Se utiliza un Java Record para `Battery` y `Joltage Strategy`.
- No existen variables de clase mutables que mantengan estado entre ejecuciones de diferentes líneas.
- El cálculo es puramente funcional (Entrada -> Salida).

### 4. Code Cleanliness
- Solo míralo, qué bonito, bien separado y nombrado
- Se hace la conversión de caracteres a enteros usando aritmética de caracteres (`c - '0'`) que es estándar y eficiente.
---

# Parte B: Algoritmos Voraces y Escalabilidad

## 1. Introducción al Desafío
En esta segunda parte, el objetivo evoluciona de encontrar un par de dígitos a construir el número de 12 cifras más grande posible a partir de la secuencia de la batería. Para lograrlo, hemos implementado una **estrategia de selección voraz (Greedy)**.

El diseño destaca por su **bajo acoplamiento**, ya que el servicio encargado de procesar las baterías no ha necesitado cambios; simplemente hemos "inyectado" una nueva lógica de cálculo.

## 2. El Corazón del Algoritmo: `GreedySelectionStrategy`
Este componente es una implementación de la interfaz `JoltageStrategy`. Su lógica se basa en la toma de decisiones óptimas locales para alcanzar un máximo global:

- **Ventana de Búsqueda Dinámica**: El algoritmo calcula un `searchLimit` en cada iteración. Esto garantiza que, aunque busquemos el dígito más alto, siempre dejemos caracteres suficientes a la derecha para completar las 12 posiciones requeridas.
- **Búsqueda Voraz**: Dentro de cada ventana, el código rastrea el carácter con el valor ASCII más alto.
- **Optimización de Salida Temprana**: Si el algoritmo encuentra un '9', deja de buscar en la ventana actual inmediatamente, ya que no existe un dígito superior en base 10.

## 3. Reutilización e Inversión de Control
Gracias a que el sistema depende de **abstracciones** en lugar de clases concretas, la integración de la Parte B ha sido inmediata:
- Utilizamos el `BatteryParser` original para transformar el texto.
- El `JoltageService` sigue siendo el orquestador que mapea y suma los resultados.
- La entidad `Battery` permanece inmutable, sirviendo como un contenedor de datos fiable y sin efectos secundarios.

## 4. Evolución Técnica y Tipado
Un aspecto crítico en esta fase ha sido el manejo de la **capacidad de datos**:
- **De `int` a `long`**: Dado que el resultado es un número de 12 dígitos, sobrepasamos los 2.147 millones que permite un entero estándar. Usar `long` asegura la precisión y evita errores de desbordamiento.
- **Eficiencia en Strings**: Se utiliza `StringBuilder` para construir el número final antes de su conversión, optimizando el uso de memoria en comparación con la concatenación repetida de Strings.

---
*Este diseño no solo resuelve el problema actual, sino que establece una infraestructura donde nuevos algoritmos de análisis de energía pueden integrarse en segundos.*