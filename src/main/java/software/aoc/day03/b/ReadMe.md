# Advent of Code Day 03 (Parte B)

En esta segunda parte, el problema cambia de una búsqueda local a una construcción global de una secuencia numérica.

## Patrones y Estrategias

### Strategy Pattern (`GreedySelectionStrategy`)
La lógica para seleccionar los 12 dígitos la he implementado como una estrategia específica.
- **Algoritmo Greedy:** Itera 12 veces (una por cada posición del número final).
- **Ventana Deslizante Dinámica:** En cada paso, calcula el rango máximo donde puede buscar el siguiente dígito (`searchLimit`), asegurándose de dejar suficientes caracteres restantes en la cadena original para completar los 12 dígitos requeridos.
- **Short-Circuit:** Si encuentra un '9' dentro del rango válido, lo selecciona inmediatamente y deja de buscar en esa iteración, ya que es el valor máximo posible.

### Inmutabilidad y Tipos
- Se ha cambiado el tipo de retorno de `int` a `long` para evitar desbordamiento numérico, dado que un número de 12 dígitos excede la capacidad de un `Integer` (máx 2.147 millones vs 12 dígitos que son billones).