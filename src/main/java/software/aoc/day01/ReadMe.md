# Advent of Code - Solución Día 01

## Introducción y Modelo de Diseño
Este proyecto resuelve el reto del Día 01 utilizando un modelo de **Arquitectura de Dominio Rico e Inmutable**. A diferencia de un modelo anémico donde los objetos solo guardan datos, aquí el objeto `Dial` posee comportamiento.

El sistema sigue un flujo lineal claramente definido:

**Entrada (IO) → Parsing → Servicio de Dominio → Resultado**

---

## Principios SOLID y Limpieza de Código
Para asegurar que este código sea mantenible, se han aplicado cuatro pilares fundamentales:

- **SRP (Responsabilidad Única)**  
  Cada archivo hace una sola cosa. El parser no sabe de matemáticas, y el dial no sabe de archivos de texto.

- **OCP (Abierto/Cerrado)**  
  Gracias al uso del `Enum Direction`, si el reto mañana añade una dirección `"UP"`, solo añadimos una constante al enum sin tocar la lógica de movimiento del `Dial`.

- **Inmutabilidad**  
  Se utiliza `record` para las instrucciones y se devuelven nuevas instancias de `Dial` en cada movimiento. Esto evita efectos secundarios y errores de estado compartido.

- **Separación de Preocupaciones**  
  El `Main` actúa únicamente como “pegamento”, delegando todo el trabajo a componentes especializados.

---

## Análisis Detallado del Código

### 1. El Corazón: `Dial.java`
Es la entidad principal del dominio. Almacena la `position` actual.

- **`move()`**  
  Recibe una instrucción, delega el cálculo matemático a la dirección y devuelve un nuevo `Dial`. Es el motor de cambio de estado inmutable.

- **`calculateScore()`**  
  Regla de negocio pura. Si la posición es `0`, suma un punto; en caso contrario, suma `0`.

---

### 2. El Motor: `Direction.java`
Un `enum` que no solo nombra direcciones (`LEFT`, `RIGHT`), sino que contiene la inteligencia del movimiento.

- **`fromChar()`**  
  Factoría que traduce caracteres del archivo (`'L'`, `'R'`) a constantes del lenguaje.

- **`calculateNewPosition()`**  
  Implementa aritmética modular (usando `floorMod` para evitar problemas con números negativos) y garantiza que la posición se mantenga siempre entre `0` y `99`.

---

### 3. La Estructura: `Instruction.java`
Un `record` simple e inmutable.  
Empareja una dirección con una cantidad de pasos y define el lenguaje que entiende el sistema.

---

### 4. El Traductor: `InstructionParser.java`
Componente encargado de limpiar el ruido externo.

- **`parseAll()`**  
  Recibe una lista de `String` (provenientes del archivo) y utiliza *Streams* de Java para:
    - Limpiar espacios
    - Filtrar líneas vacías
    - Convertir cada línea en un objeto `Instruction`

- **`parseSingle()`**  
  Usa lógica de `substring` para separar el primer carácter (la letra de dirección) del resto (el número de pasos).

---

### 5. El Orquestador: `PasswordService.java`
Es el cerebro de la operación.

- Recibe una posición inicial y la lista de instrucciones.
- Itera sobre ellas, actualizando la referencia del `Dial` y acumulando el `score`.
- Conoce **cómo** se resuelve el puzzle completo, pero no **cómo** se mueve el dial internamente.

---

### 6. El Director: `Main.java`
Punto de entrada de la aplicación. Sus responsabilidades son:

- Instanciar las dependencias.
- Leer el archivo físico de disco (`Orders.txt`).
- Llamar al parser y luego al servicio de dominio.
- Imprimir el resultado final en la consola.

---

# Parte B

## Introducción: Del Estado a la Transición
En la **Parte A**, solo importaba dónde terminaba el dial. En esta **Parte B**, el reto evoluciona: ahora debemos contar cuántas veces el dial *pasa por el cero* mientras se mueve.

Este nuevo requerimiento cambia fundamentalmente el diseño, ya que deja de ser suficiente conocer el estado final y pasa a ser crítico analizar **cada transición** durante el movimiento.

---

## Evolución de los Principios SOLID

- **Reutilización (DRY)**  
  No se ha reescrito ni el `InstructionParser` ni `Instruction`. El código de la Parte B importa y reutiliza directamente los componentes de la Parte A, demostrando la fortaleza de un diseño desacoplado.

- **Responsabilidad Única (SRP) Refinada**  
  El `Dial` ahora no solo calcula su nueva posición, sino que también calcula los *hits* (cruces por cero).  
  Sin embargo, **no mantiene el total acumulado**: se limita a informar qué ocurrió en ese turno específico.

- **Encapsulamiento de la Complejidad**  
  La lógica matemática para detectar cruces por cero, especialmente en movimientos hacia la izquierda, es compleja.  
  En lugar de contaminar el servicio, esta lógica vive en métodos privados dentro del `Dial`, protegiendo la integridad del dominio.

---

## Análisis Detallado del Código

### 1. El Objeto de Dominio: `Dial.java` (Record)
A diferencia de la Parte A, aquí el `Dial` se implementa como un `record`.

- **Validación**  
  El constructor compacto asegura que nunca exista un `Dial` con una posición menor a `0` o mayor a `99`.

- **`turn()`**  
  Es la función maestra. Recibe una instrucción y, en lugar de devolver solo un `Dial`, devuelve un `TurnResult`.

- **Lógica de `calculateHits()`**
    - **RIGHT**  
      La lógica es directa: se suma el desplazamiento y se divide por el tamaño del dial (`100`) para calcular cuántas vueltas completas se realizaron.
    - **LEFT**  
      La lógica es ligeramente más compleja
    - .  
      Se calcula primero la distancia hasta el cero. Si el movimiento supera esa distancia:
        - Se cuenta el primer cruce.
        - Luego se cuentan los cruces adicionales según las vueltas completas restantes.

---

### 2. El Contenedor: `TurnResult.java`
Es un objeto de transporte inmutable (`record`).

Dado que un método en Java solo puede devolver un valor y aquí necesitamos devolver dos:
- El nuevo `Dial`
- Los puntos (hits) obtenidos en ese turno

Es clave para mantener la inmutabilidad: el `Dial` **no modifica ningún estado global**, solo reporta lo ocurrido.

---

### 3. El Servicio: `PasswordServiceB.java`
Este servicio actúa como un contador histórico.

- Mantiene una variable `totalHits`.
- En cada iteración:
    - Recibe un `TurnResult`
    - Extrae los puntos ganados en ese movimiento
    - Actualiza la referencia del `Dial` para la siguiente instrucción
- Al finalizar, devuelve el total acumulado de cruces por cero.

---

### 4. La Orquestación: `Main.java`
El `Main` de la Parte B es un ejemplo claro de interoperabilidad.

- Instancia el `InstructionParser` de la Parte A.
- Instancia el `PasswordServiceB` de la Parte B.
- Lee el mismo archivo de órdenes, demostrando que la infraestructura permanece intacta aunque la lógica de negocio sea completamente distinta.

---

## Conclusión sobre la Evolución del Código
La **Parte B** es conceptualmente más robusta.  
Mientras que la Parte A era un cálculo de *foto fija* al final de cada movimiento, la Parte B es un cálculo de *película*, donde importa todo el trayecto recorrido.

El uso de `TurnResult` permite que el sistema crezca en complejidad sin sacrificar claridad, inmutabilidad ni limpieza arquitectónica, confirmando la solidez del diseño original.
