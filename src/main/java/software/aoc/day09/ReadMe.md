# Advent of Code - Día 09: Geometría Computacional y Optimización Espacial

Este proyecto implementa soluciones para problemas geométricos, aplicando principios de diseño que garantizan la flexibilidad del algoritmo y la eficiencia en el manejo de coordenadas.

---

# Parte A: Cálculo de Áreas y Estrategias Flexibles

## 1. Introducción al Diseño
El objetivo de la primera parte es encontrar el rectángulo de mayor área posible a partir de una nube de puntos. El diseño prioriza el **Bajo Acoplamiento** y la **Abstracción**, permitiendo que la lógica de selección del algoritmo sea independiente del flujo principal del programa.

## 2. Componentes y Principios Aplicados

### `Point` (Modelo y Creación)
Implementado como un `record` para asegurar la inmutabilidad de los datos.
- **Patrón Factory Method**: Utilizamos el método estático `parse(String line)` en lugar de un constructor complejo. Este método estático encapsula la lógica de creación del objeto, un enfoque alineado con la definición del patrón Factory Method donde se utiliza un método estático para crear instancias.

### `GeometryService` y `AreaStrategy` (Inversión de Dependencias)
El servicio de geometría no implementa el algoritmo de búsqueda directamente.
- **Principio de Inversión de Dependencias (DIP)**: `GeometryService` depende de la abstracción `AreaStrategy`, no de la clase concreta `BruteForcePairsStrategy`. Esto cumple con el principio de que los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones.
- **Principio Abierto/Cerrado (OCP)**: El diseño permite la extensión (nuevas estrategias de cálculo) sin modificar el código del servicio, manteniendo las clases cerradas para modificación pero abiertas para extensión.

### `BoundingBox` (Alta Cohesión)
- **Alta Cohesión**: Esta clase agrupa los datos (puntos p1, p2) y la lógica relacionada (cálculo de área) en una única entidad. Esto refleja la idea de que las partes de un módulo deben estar estrechamente relacionadas y enfocadas en una tarea.

---

# Parte B: Restricciones Espaciales y Compresión

## 1. Evolución del Problema
En la Parte B, se introduce una restricción compleja: el rectángulo debe estar contenido dentro de un polígono arbitrario. Esto requiere algoritmos más sofisticados (`RayCaster`) y estructuras optimizadas (`CompressedGrid`).

## 2. Arquitectura y Reutilización

### `RayCaster` (Responsabilidad Única)
Esta clase aisla la complejidad matemática del algoritmo "Even-Odd" para detectar si un punto está dentro de un polígono.
- **Principio de Responsabilidad Única (SRP)**: La clase tiene una sola razón para cambiar (si cambia la forma de calcular la intersección geométrica), separando esta lógica del resto del sistema.

### `CompressedGrid` (Optimización)
Maneja la complejidad de mapear coordenadas dispersas a una matriz lógica manejable.
- **Modularidad**: Promueve la división del sistema en componentes independientes, donde `CompressedGrid` se encarga exclusivamente de la representación espacial eficiente.

### `Main` y Reutilización
- **Principio de No Repetir Código (DRY)**: El sistema reutiliza componentes de la Parte A como `PointParser`, `Point` y `BoundingBox`. Esto se alinea con el principio de que cada pieza de conocimiento debe tener una representación única e inequívoca en el sistema.

### `ConstrainedSolver` (Programación Funcional)
Orquesta la solución utilizando flujos paralelos (`parallel stream`).
- **Inmutabilidad**: El uso de `records` (definidos en la Parte A) facilita que el procesamiento paralelo sea seguro, ya que los objetos no cambian de estado, un fundamento clave para un código limpio y mantenible.

---

## Resumen de Fundamentos

| Concepto | Implementación | Referencia Teórica |
| :--- | :--- | :--- |
| **Factory Method** | `Point.parse` | Encapsula la creación de objetos. |
| **DIP** | `GeometryService` -> `AreaStrategy` | Dependencia de abstracciones. |
| **SRP** | `RayCaster` | Separación de lógica matemática compleja. |
| **OCP** | Estrategias intercambiables | Abierto a extensión, cerrado a modificación. |
| **Alta Cohesión** | `BoundingBox` | Datos y comportamiento juntos. |