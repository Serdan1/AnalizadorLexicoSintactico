# Analizador Léxico y Sintáctico en Java

Este proyecto implementa un compilador básico dividido en dos fases: un **Scanner (Analizador Léxico)** y un **Parser (Analizador Sintáctico)**. El programa es capaz de leer instrucciones de código fuente, tokenizarlas y validar su estructura gramatical mediante un descenso recursivo.

## 📋 Características

* **Análisis Léxico:** Convierte el código fuente en una secuencia de tokens clasificados (Palabras clave, Identificadores, Números, Operadores y Delimitadores).
* **Análisis Sintáctico:** Valida que la secuencia de tokens respete la gramática definida.
* **Soporte de Expresiones:** Admite operaciones aritméticas complejas (`+`, `-`, `*`, `/`) con precedencia de operadores y uso de paréntesis `()`.
* **Recuperación de Errores:** Implementa *Panic Mode Recovery*, permitiendo detectar múltiples errores en distintas sentencias sin detener la ejecución en el primer fallo.

## 🔧 Requisitos Técnicos

* **Lenguaje:** Java (JDK 8 o superior).
* **Entorno:** Cualquier IDE (IntelliJ IDEA, Eclipse) o terminal de comandos.
* **Dependencias:** Ninguna (solo librerías estándar de Java).

## 🚀 Instrucciones de Ejecución

### Desde IntelliJ IDEA
1.  Abrir el proyecto.
2.  Localizar la clase principal: `src/Main.java`.
3.  Ejecutar el método `main`.

### Desde la Terminal (Consola)
1.  Navegar a la carpeta del proyecto.
2.  Compilar los archivos:
    ```bash
    javac src/*.java -d out
    ```
3.  Ejecutar el programa:
    ```bash
    java -cp out Main
    ```

## 📖 Gramática Soportada

El analizador valida las siguientes estructuras:

1.  **Asignaciones:**
    ```text
    variable = expresión;
    ```
    *Ejemplo:* `x = 10 + 5 * (2 - 1);`

2.  **Impresión:**
    ```text
    print(expresión);
    ```
    *Ejemplo:* `print(x);`

### Jerarquía de Operaciones (Precedencia)
1.  Paréntesis `()`
2.  Multiplicación `*` y División `/`
3.  Suma `+` y Resta `-`

## 🧪 Ejemplo de Uso

**Entrada:**
```text
x = 10 + 2 * 5;
print(x);
y = 5 + ;
print(y);
```

**Salida:**
```text
--- 1. ANÁLISIS LÉXICO (Tokens) ---
Token: <IDENTIFICADOR, "x">
Token: <OPERADOR, "=">
Token: <LITERAL_NUMERICO, "10">
...

--- 2. ANÁLISIS SINTÁCTICO ---
>> Sentencia ASIGNACIÓN válida
>> Sentencia PRINT válida
Error de sintaxis: Se esperaba un número, variable o paréntesis, pero se encontró: ;
>> Intentando recuperarse del error...
>> Sentencia PRINT válida
```

