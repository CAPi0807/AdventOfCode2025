# Refactorización SOLID - Advent of Code Day 02 (Parte B)

La implementación de la Parte B destaca la reutilización de código gracias a la inversión de dependencias.

## Diferencias Clave

### Predicado de Patrones (`PatternRepetitivePredicate`)
A diferencia de la Parte A, que solo dividía el número en dos mitades, esta clase implementa una lógica más robusta:
1. Itera sobre todos los posibles tamaños de "chunk" desde 1 hasta la mitad de la longitud del número.
2. Si la longitud total es divisible por el tamaño del chunk, extrae el patrón potencial.
3. Utiliza una técnica de reducción de strings (`replace`) para verificar si el número original está compuesto exclusivamente por repeticiones de ese patrón.

### Reutilización del Servicio
La clase `RangeSumService` **no ha sido modificada**. Solo se le ha inyectado una implementación diferente de `NumberPredicate` en el `Main`. Esto confirma que la clase cumple con el principio OCP (abierta a extensión, cerrada a modificación).