# Advent of Code - Día 11 Parte B: Waypoint Routing

En la Parte B, el problema se complica: ya no basta con ir del inicio al fin. Debemos encontrar el número de caminos que van desde `svr` hasta `out` pasando **obligatoriamente** por dos puntos de control: `fft` y `dac`.

## Lógica Matemática y Diseño

### Principio de Multiplicación (Combinatoria)
Dado que el grafo es un DAG (Grafo Acíclico Dirigido), podemos descomponer el problema en etapas independientes.
Para ir de A a C pasando por B, el número total de caminos es:
$$Caminos(A \to C) = Caminos(A \to B) \times Caminos(B \to C)$$

### Estrategia de Resolución (`Day11BSolver`)
Como no sabemos en qué orden debemos visitar los puntos de control, calculamos las dos permutaciones posibles y las sumamos:

1.  **Variante 1**: `svr` $\to$ `fft` $\to$ `dac` $\to$ `out`
    * Cálculo: $Count(svr \to fft) \times Count(fft \to dac) \times Count(dac \to out)$
2.  **Variante 2**: `svr` $\to$ `dac` $\to$ `fft` $\to$ `out`
    * Cálculo: $Count(svr \to dac) \times Count(dac \to fft) \times Count(fft \to out)$

El resultado final es la suma de ambas variantes. Si una variante es imposible (por la dirección de las flechas), su conteo será 0 y no afectará a la suma.

### Principios SOLID & Clean Code
- **DRY (Don't Repeat Yourself)**: Reutilizamos íntegramente `Graph`, `GraphParser` y `PathCounter` del paquete `a`. No se ha duplicado código de infraestructura.
- **SRP (Single Responsibility)**: `Day11BSolver` no sabe contar caminos recursivamente; delega esa tarea compleja a `PathCounter` y se limita a orquestar la lógica de negocio de los "Waypoints".

## Documentación de Archivos

### 1. `Main.java` (Parte B)
* **Función**: Punto de entrada específico para la Parte B.
* **Detalles**: Lee el archivo y llama al solver especializado.

### 2. `Day11BSolver.java`
* **Función**: Servicio de Rutas Compuestas.
* **Detalles**:
    - Define las constantes de los nodos clave (`svr`, `fft`, `dac`, `out`).
    - Divide el problema global en 3 sub-problemas más pequeños para cada variante.
    - Maneja la lógica aritmética (multiplicación de segmentos y suma de variantes).