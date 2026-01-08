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
*Este proyecto demuestra cómo convertir un script de resolución de problemas en una pieza de software de grado industrial.*