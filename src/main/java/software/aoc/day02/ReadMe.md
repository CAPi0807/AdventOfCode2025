# Advent of Code - Día 02: Suma de Rangos Repetitivos

## 1. Introducción y Arquitectura
En este segundo día, nos enfrentamos a un problema de procesamiento de datos numéricos a gran escala. A diferencia del Día 01, donde el estado era persistente, aquí trabajamos con un modelo **Stateless** (sin estado).

Hemos aplicado una arquitectura basada en el **Principio de Inversión de Dependencias (DIP)**. El servicio principal no depende de una lógica rígida, sino de una abstracción (`NumberPredicate`), lo que nos permite cambiar las reglas de filtrado de números sin tocar una sola línea del motor de cálculo.

## 2. El Dominio: Inteligencia en los Datos 

### `NumericRange.java` (El Record)
No es un simple contenedor. Este Record incluye lógica de **autocorrección**: si el usuario introduce un rango al revés (ej. 50 a 10), el constructor lo detecta y lo normaliza automáticamente. Además, expone un método `stream()` que convierte el rango en una secuencia de números procesable, ocultando la complejidad del bucle al resto del sistema.

### `NumberPredicate.java` y su Implementación
Utilizamos una **Interfaz Funcional**. La clase `RepetitiveNumberPredicate` implementa la lógica de "números espejo" (como 1212 o 4545).
- Primero, valida la longitud (debe ser par y mayor a 2).
- Segundo, divide el número físicamente en dos mitades usando manipulación de Strings y comprueba su igualdad.

## 3. Infraestructura y Parsing 

### `RangeParser.java`
Este componente se encarga de la "limpieza" inicial. Recibe un String complejo (ej: "1-5,10-15") y realiza una doble división:
1. Divide por comas para separar los bloques.
2. Divide cada bloque por el guion para extraer los límites.
   Es un componente puramente funcional que transforma texto bruto en una lista de objetos `NumericRange`.

## 4. El Motor de Cálculo: `RangeSumService`
Aquí es donde ocurre la magia de Java Streams. El método `calculateUniqueSum` realiza cuatro operaciones críticas en una sola tubería de datos:
1. **`flatMapToLong`**: Aplana todos los rangos en una única lista gigante de números individuales.
2. **`filter`**: Aplica el predicado inyectado para dejar pasar solo los números repetitivos.
3. **`distinct`**: Elimina duplicados si los rangos se solapaban.
4. **`sum`**: Reduce todo a un único resultado final.

## 5. Orquestación y SOLID

### `Main.java`
El Main actúa como el **Composition Root**. Aquí es donde decidimos qué estrategia usar. Instanciamos el parser, el predicado y el servicio.
Destaca el manejo de errores: hemos implementado un bloque `try-catch` robusto que diferencia entre errores de lectura de archivo (IO) y errores de formato numérico, asegurando que el programa no falle silenciosamente.

### Resumen de Principios
- **SRP**: El servicio suma, el predicado valida, el parser traduce.
- **OCP**: ¿Mañana queremos sumar solo números primos? Solo creamos un `PrimeNumberPredicate` y lo pasamos al constructor del servicio. El código existente no se toca.
- **Inmutabilidad**: Al usar Records y Streams, eliminamos el riesgo de errores por variables que cambian de valor inesperadamente.

---
# Parte B: Patrones Repetitivos Dinámicos

## 1. Introducción: El Poder de la Inyección
En esta Parte B, el reto de encontrar números "repetitivos" se vuelve más complejo. Ya no buscamos solo números partidos en dos (como 1212), sino números formados por cualquier patrón que se repita N veces (como 123123123 o 777).

Lo más destacable de esta solución es que **no hemos modificado ni una sola línea** del servicio de suma o del parser del Día 02-A. Hemos aplicado el patrón de diseño **Strategy**, cambiando simplemente la "estrategia" de filtrado.



## 2. El Nuevo Corazón: `PatternRepetitivePredicate`
Este archivo contiene la lógica más sofisticada del día. A diferencia de la versión anterior que era rígida, esta implementación utiliza un enfoque de **búsqueda de patrones dinámicos**:

- **Divisibilidad**: El código es eficiente porque solo intenta crear patrones con tamaños que sean divisores exactos de la longitud total del número. Si un número tiene 6 dígitos, solo prueba patrones de tamaño 1, 2 y 3.
- **Validación por Reemplazo**: En lugar de hacer comparaciones complejas en bucles, extrae un patrón inicial y lo borra de todo el string original (`replace(pattern, "")`). Si al final el string queda vacío, significa que el número estaba construido puramente por ese bloque.
- **Cortocircuito**: Gracias a `anyMatch`, el proceso se detiene en cuanto encuentra el primer patrón válido, optimizando el tiempo de ejecución.

## 3. Reutilización de Componentes de la Parte A (2:30 - 3:30)
Aquí es donde la arquitectura limpia brilla. En el `Main`, importamos clases del paquete `day02.a`:
- **`RangeParser`**: Sigue cumpliendo su función de trocear el archivo de texto.
- **`NumericRange`**: Sigue gestionando los límites y creando los Streams de datos.
- **`RangeSumService`**: Sigue orquestando el filtrado y la suma única.

Esta es la prueba real del principio **Open/Closed**: el sistema creció en funcionalidades (ahora soporta patrones complejos) sin necesidad de modificar el código existente.

## 4. Orquestación y Flujo de Datos

### `Main.java` (La Fábrica)
El Main actúa como un ensamblador de piezas.
1. Crea la nueva "estrategia" (`PatternRepetitivePredicate`).
2. Se la entrega al servicio antiguo (`RangeSumService`).
3. Ejecuta el proceso de IO y Parsing.

El flujo de datos se mantiene idéntico:
**Texto → Lista de Rangos → Stream de Números → Filtro de Patrones → Suma Única.**



## 5. Conclusión y Reflexión SOLID
- **DIP (Inversión de Dependencias)**: El éxito de esta refactorización se debe a que el servicio depende de la interfaz `NumberPredicate` y no de una clase concreta.
- **DRY (Don't Repeat Yourself)**: Al no copiar y pegar el código de la suma o el parser, cualquier mejora futura en el rendimiento del servicio beneficiará automáticamente a ambas partes (A y B).
- **Escalabilidad**: Si el Día 03 nos pidiera filtrar por números primos o palíndromos, solo tendríamos que crear una nueva clase que implemente la interfaz.

---
*Este diseño minimiza el coste de mantenimiento y maximiza la reutilización, pilares del software profesional.*