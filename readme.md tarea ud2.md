# Tarea 3: Factura Java

**Usuario de GitHub:** davidcoca  

---

## Reto 1 · Compilar desde la terminal

### Preguntas
* **¿Qué es `Factura.java`?**  
  Es el archivo de código fuente escrito en lenguaje de programación Java legible por los desarrolladores.
* **¿Qué será `Factura.class`?**  
  Es el archivo ejecutable resultante de la compilación que contiene el *bytecode* de Java (código de bajo nivel interpretable por la máquina virtual).
* **¿Quién ejecuta el `.class`?**  
  La Máquina Virtual de Java (**JVM** - Java Virtual Machine).
* **¿Por qué no se ha generado el archivo `.class` tras el primer intento?**  
  Porque el archivo fuente `Factura.java` contenía errores de compilación (sintácticos y léxicos) que impidieron al compilador (`javac`) generar el bytecode.

---

## Reto 2 · Encontrar y clasificar los errores de compilación

| Línea | Mensaje de error de `javac` | Tipo de error | Corrección realizada |
| :---: | :--- | :---: | :--- |
| **12** | `error: illegal character: '#'` | Léxico | Se eliminó el carácter ilegal `#` del nombre de la variable (`precio#` pasó a ser `precio`). |
| **14** | `error: ';' expected` | Sintaxis | Se añadió el punto y coma `;` al final de la instrucción `int cantidad = sc.nextInt()`. |
| **23** | `error: incompatible types: possible lossy conversion from double to int` | Semántico | Se cambió el tipo de dato de la variable `total` de `int` a `double` (`double total = ...`), ya que la operación involucra números decimales. |

### Prueba de ejecución (Reto 2)
* **Valores introducidos:** Precio: `60,0`, Cantidad: `2`, Socio: `s`.
* **Resultado del programa:** `137,94 €` (Incorrecto debido a un error lógico).
* **Cálculo manual correcto:**  
  - Base: $60 \times 2 = 120,00\text{ €}$  
  - Descuento: $10\% \text{ (base } > 100) + 5\% \text{ (socio)} = 15\%$ de $120\text{ €} = 18,00\text{ €}$  
  - Base con descuento: $120 - 18 = 102,00\text{ €}$  
  - Total con IVA ($21\%$): $102 \times 1,21 = 123,42\text{ €}$

---

## Reto 3 · Depurar el error lógico del descuento

### Análisis del fallo lógico
El error estaba en la lógica de evaluación del descuento:
1. Al cumplirse `base > 100`, la variable `descuento` se calculaba como `base * 0.10`.
2. A continuación, el segundo bloque `if (socio.equals("s"))` **sobrescribía** el valor de `descuento` asignándole `base * 0.05` en lugar de acumularlo, ignorando el descuento por base.

### Comparativa de valores en depuración

| Estado | Valor Esperado | Valor Real (antes del fix) |
| :--- | :---: | :---: |
| Tras evaluar `if (base > 100)` | `12,00 €` (10%) | `12,00 €` |
| Tras evaluar `if (socio.equals("s"))` | `18,00 €` (15%) | `6,00 €` (Sobrescrito con el 5%) |
| Total final con IVA | `123,42 €` | `137,94 €` |

### Prueba propia de verificación
* **Producto:** Monitor 4K
* **Precio unitario:** `150,0`
* **Cantidad:** `1`
* **Socio:** `s`
* **Cálculo:**  
  - Base: $150,00\text{ €}$  
  - Descuento ($15\%$): $22,50\text{ €}$  
  - Base neta: $127,50\text{ €}$  
  - Total con IVA ($21\%$): $154,275 \approx 154,28\text{ €}$

---

## Reto 4 · Reutilizar código con métodos

### Métodos implementados
* `static double calcularDescuento(double base, boolean socio)`:  
  Calcula y devuelve el importe total del descuento acumulado según el volumen de la base imponible y el estado de socio del cliente.
* `static double aplicarIVA(double importe)`:  
  Aplica la tasa constante de IVA ($21\%$) al importe neto y devuelve el total final.

### Ventajas de la refactorización
1. **Modularidad y Mantenibilidad:** Se separa la lógica de negocio (cálculos) de la interfaz de entrada/salida (`main`), facilitando modificar reglas fiscales o porcentajes en un solo lugar.
2. **Reutilización y Legibilidad:** El código del método `main` es mucho más limpio, fácil de entender y permite reutilizar las funciones de cálculo en otras partes de la aplicación sin duplicar código.

---

## Reto 5 · ¿Qué lenguaje elegirías?

### Caso A: Calculadora de presupuesto en el navegador
* **Tipo de lenguaje recomendado:** **Interpretado / Lenguaje de cliente web** (JavaScript / TypeScript).
* **Justificación basada en criterios:**
  1. **Portabilidad y Compatibilidad:** Se ejecuta de forma nativa en cualquier navegador web y dispositivo del usuario sin necesidad de instalar entornos de ejecución como la JVM.
  2. **Coste de desarrollo e Interacción:** Ofrece integración inmediata con la interfaz HTML/CSS del sitio web para proporcionar una respuesta instantánea en tiempo real al usuario.

### Caso B: Reescribir la calculadora en ensamblador
* **Evaluación de la propuesta:** No es aconsejable utilizar ensamblador para esta aplicación.
* **Justificación basada en criterios:**
  1. **Coste de desarrollo y Mantenibilidad:** El coste en horas de desarrollo y la complejidad de escribir y mantener código en ensamblador es desproporcionadamente alto para operaciones aritméticas básicas de facturación.
  2. **Portabilidad:** El código ensamblador está ligado a la arquitectura específica del procesador (x86, ARM, etc.), perdiendo la portabilidad universal que ofrece un lenguaje como Java en cualquier sistema operativo.