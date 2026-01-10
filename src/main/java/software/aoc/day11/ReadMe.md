# Advent of Code - Día 11: Navegación de Grafos y Memoización

## Parte A: Conteo de Rutas en Grafos Dirigidos

### 1. El Modelo de Grafo
La estructura del problema se basa en un **grafo dirigido**, donde cada nodo representa una ubicación y las aristas definen las rutas posibles entre ellas.

- **`Graph`**  
  Utiliza una lista de adyacencia **inmutable** para representar las interdependencias con bajo acoplamiento y alta seguridad frente a efectos secundarios.

- **`GraphParser`**  
  Emplea *Java Streams* para procesar el formato de texto de entrada y construir eficientemente el mapa de conexiones entre nodos.

---

### 2. Algoritmo de Conteo con Memoización: `PathCounter`
Calcular todos los caminos posibles en un grafo dirigido puede provocar una explosión combinatoria.  
Para evitarlo, se implementa un algoritmo recursivo optimizado que aplica memoización y sigue el patrón *Strategy*.

- **Caso Base**  
  Cuando el nodo actual coincide con el nodo destino, el método retorna `1`, indicando que se ha encontrado una ruta válida.

- **Memoización**  
  Se utiliza una caché (`Map<String, Long>`) para almacenar resultados parciales.  
  Si el número de caminos desde un nodo ya fue calculado previamente, el valor se devuelve directamente sin recalcularlo.

- **Recursividad**  
  El número total de caminos desde un nodo es la suma de los caminos posibles desde cada uno de sus vecinos inmediatos.

---

## Parte B: Rutas Complejas con Puntos de Paso

### 1. Reutilización y Modularidad (DRY)
La Parte B demuestra el **Principio Abierto/Cerrado (OCP)** al extender la funcionalidad sin modificar las clases base del grafo ni el contador de rutas.

Se reutiliza íntegramente el motor de conteo de la Parte A para resolver un problema más complejo basado en la **composición de rutas**.

---

### 2. Composición de Caminos (Waypoints)
Para calcular rutas que deben pasar obligatoriamente por dos puntos intermedios (`fft` y `dac`), se aplica el **Principio de Responsabilidad Única** separando claramente cada cálculo.

- **Cálculo por Tramos**  
  La ruta completa se divide en segmentos independientes:
    - Inicio → Punto 1
    - Punto 1 → Punto 2
    - Punto 2 → Fin

- **Multiplicación de Resultados**  
  El número total de rutas de una variante es el producto del número de caminos de cada tramo independiente.

- **Variantes Excluyentes**  
  Se evalúan las dos secuencias posibles de visita a los puntos intermedios:
    - Inicio → fft → dac → Fin
    - Inicio → dac → fft → Fin

  Los resultados de ambas variantes se suman para obtener el total global.

---

## Fundamentos y Principios Aplicados

- **Dependency Inversion Principle (DIP)**  
  Los servicios de alto nivel dependen de la abstracción del contador de rutas, permitiendo que la lógica de búsqueda sea independiente de cómo se calculan o componen los tramos.

- **Inmutabilidad**  
  El uso de `Map.copyOf` en la construcción del grafo y la gestión controlada de estados intermedios garantizan que los datos no sufran modificaciones inesperadas durante las múltiples llamadas recursivas.

- **Código Expresivo y Limpio**  
  El sistema separa claramente:
    - Infraestructura (`GraphParser`)
    - Estructura de datos (`Graph`)
    - Lógica de negocio (`PathCounter`)

  Esto facilita el mantenimiento, la extensibilidad y una lectura profesional del código.

---

## Conclusión
Este enfoque transforma un problema clásico de búsqueda en grafos en una solución de **alto rendimiento**, combinando memoización, reutilización de algoritmos y diseño orientado a principios SOLID para gestionar eficientemente tanto la complejidad computacional como la claridad arquitectónica.
