# Advent of Code - Solución Día 01 (Refactorizada)

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
