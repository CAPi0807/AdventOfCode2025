# Advent of Code - Solución Día 01 (Parte B)

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
      La lógica es más sutil.  
      Se calcula primero la distancia hasta el cero. Si el movimiento supera esa distancia:
        - Se cuenta el primer cruce.
        - Luego se cuentan los cruces adicionales según las vueltas completas restantes.

---

### 2. El Contenedor: `TurnResult.java`
Es un objeto de transporte inmutable.

Dado que un método en Java solo puede devolver un valor y aquí necesitamos devolver dos:
- El nuevo `Dial`
- Los puntos (hits) obtenidos en ese turno

Se introduce este `record`.  
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
