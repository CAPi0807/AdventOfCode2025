## Principios Implementados

### 1. Single Responsibility Principle (SRP)
Cada clase tiene una única razón para cambiar:
- `InstructionParser`: Solo sabe cómo convertir texto en objetos.
- `Dial`: Solo contiene el estado (posición) y sabe calcular su propio score.
- `Direction`: Contiene la lógica matemática de movimiento.
- `PasswordService`: Contiene la lógica de negocio (aplicar secuencia y acumular score).

### 2. Open/Closed Principle (OCP)
El sistema está abierto a extensión pero cerrado a modificación.
- La lógica de direcciones está encapsulada en el Enum `Direction`. Si se quisiera agregar una dirección nueva, solo se modifica el Enum, sin riesgo de romper la clase `Dial` o el parser.

### 3. Inmutabilidad
- La clase `Dial` es inmutable. El método `move()` no cambia el objeto actual, sino que devuelve una nueva instancia con la nueva posición.
- `Instruction` es un Java Record, inmutable por defecto.

### 4. Modularidad
- El código original mezclaba lectura de archivos, parsing y lógica en `Dial` y `Main`.
- Ahora, `Main` solo orquesta. El parsing y la lógica están en componentes separados.