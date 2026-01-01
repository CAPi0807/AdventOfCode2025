
## Diferencias de Diseño respecto a Parte A

### 1. Cambio de Lógica de Puntuación
- **Parte A:** Puntuación basada en el **Estado Final** (¿Terminé en 0?).
- **Parte B:** Puntuación basada en la **Transición** (¿Cuántas veces crucé por 0 durante el movimiento?).

### 2. Manejo de Inmutabilidad con Retornos Compuestos
Dado que necesitamos tanto el nuevo estado del dial como los puntos obtenidos *durante* ese movimiento específico, se ha introducido un record `TurnResult`.

- `Dial.turn(instruction)` devuelve `TurnResult(Dial newDial, int hits)`.
- Esto permite mantener `Dial` como un objeto inmutable puro (solo sabe su posición) y extraer la lógica de acumulación al servicio.

### 3. Estrategia en Enum `Direction`
El Enum `Direction` absorbe toda la complejidad matemática:
- **RIGHT:** Cálculo directo basado en la suma absoluta.
- **LEFT:** Cálculo basado en la distancia al cero y aritmética modular (`Math.floorMod`).

Esto mantiene al `PasswordServiceB` limpio, ocupándose solo de acumular resultados.