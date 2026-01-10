# Advent of Code - Día 10: Sistemas de Ecuaciones en la Maquinaria

## Parte A: El Campo de Galois GF(2)

### 1. El Problema y la Física Binaria
En la primera fase, cada botón conmuta el estado de las luces (encendido/apagado).  
Al tratarse de un sistema donde presionar dos veces un botón anula su efecto  
\[
1 + 1 = 0
\]
el problema se modela matemáticamente como un **sistema de ecuaciones lineales sobre el cuerpo finito GF(2)**.

Cada luz representa una ecuación, cada botón una variable, y la matriz de incidencia describe qué botón afecta a qué luz.

---

### 2. El Motor Matemático: `GaussianSolver`
Para encontrar el **número mínimo de pulsaciones**, se implementa una Eliminación Gaussiana adaptada a GF(2):

- **Forma Escalonada**  
  La matriz se transforma para identificar variables pivote y variables libres.

- **Minimización del Peso de Hamming**  
  Dado que el sistema puede tener múltiples soluciones (por la existencia de variables libres), se exploran combinatoriamente las \(2^k\) posibilidades para elegir la solución con el menor número de bits activos, es decir, el menor número de pulsaciones.

- **Operaciones XOR**  
  En GF(2), la suma y la resta son equivalentes a la operación bitwise XOR, lo que permite una ejecución extremadamente rápida y eficiente.

---

## Parte B: Optimización Lineal Entera

### 1. Evolución del Modelo: Voltajes Acumulativos
En la segunda parte, los botones ya no conmutan luces, sino que **incrementan voltajes**.  
Esto rompe la lógica binaria y traslada el problema a un sistema de ecuaciones lineales sobre **números enteros positivos**.

Ya no buscamos si una luz está encendida o apagada, sino **cuántas veces** debemos pulsar cada botón para alcanzar un voltaje objetivo exacto.

---

### 2. El Optimizador: `LinearOptimizer`
La resolución requiere un enfoque más sofisticado, combinando álgebra lineal clásica con búsqueda discreta:

- **Reducción Gauss-Jordan (RREF)**  
  El sistema se lleva a su forma reducida por filas utilizando aritmética de punto flotante con control explícito de precisión mediante un valor `EPSILON`.

- **Búsqueda de Soluciones Enteras**  
  Identificadas las variables libres, el sistema realiza una búsqueda exhaustiva pero acotada para encontrar combinaciones que produzcan soluciones enteras válidas.

- **Límite Dinámico de Búsqueda**  
  Se calcula un `searchLimit` basado en el voltaje objetivo máximo.  
  Esto garantiza que el algoritmo termine rápidamente al descartar combinaciones físicamente imposibles.

---

## Principios de Ingeniería Aplicados

- **Single Responsibility Principle (SRP)**  
  El `MachineParser` es el único responsable de interpretar el formato de texto complejo mediante expresiones regulares robustas.  
  Los *solvers* operan exclusivamente sobre matrices y no conocen el origen de los datos.

- **Open/Closed Principle (OCP)**  
  La infraestructura de la Parte A se extiende para la Parte B reutilizando el modelo de datos y el parser, pero inyectando un nuevo motor matemático (`LinearOptimizer`) sin modificar la lógica existente.

- **Dependency Inversion Principle (DIP)**  
  Los controladores principales dependen de abstracciones matemáticas.  
  Esto permite sustituir el algoritmo de búsqueda por métodos más avanzados (como Simplex o Branch and Bound) sin afectar al resto del sistema.

- **Inmutabilidad**  
  El uso de *Java Records* garantiza que la configuración de cada máquina sea tratada como un *snapshot* inmutable, facilitando la paralelización del procesamiento de múltiples máquinas.

---

## Conclusión
Este enfoque transforma un puzzle de lógica en un **problema de computación científica**, demostrando cómo los fundamentos del álgebra lineal —desde cuerpos finitos hasta optimización entera— permiten resolver desafíos complejos de forma determinista, eficiente y extensible.
