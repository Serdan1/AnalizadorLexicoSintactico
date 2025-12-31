import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int actual = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Método principal que inicia el análisis
    public void parse() {
        // Regla: StmtList -> Stmt StmtList | epsilon [cite: 68]
        while (!isAtEnd()) {
            try {
                sentencia();
            } catch (RuntimeException e) {
                // Muestra el error pero no detiene el programa
                System.err.println(e.getMessage());
                sincronizar(); // Intenta recuperarse para leer la siguiente línea
            }
        }
    }

    // Regla: Stmt -> ID '=' Expr ';' | 'print' '(' Expr ')' ';' [cite: 69]
    private void sentencia() {
        if (match(TipoToken.PALABRA_CLAVE) && previo().getLexema().equals("print")) {
            // Caso: print(...)
            consumir(TipoToken.DELIMITADOR, "(");
            expresion();
            consumir(TipoToken.DELIMITADOR, ")");
            consumir(TipoToken.DELIMITADOR, ";");
            System.out.println(">> Sentencia PRINT válida");
        } else if (match(TipoToken.IDENTIFICADOR)) {
            // Caso: Asignación ID = ...
            consumir(TipoToken.OPERADOR, "=");
            expresion();
            consumir(TipoToken.DELIMITADOR, ";");
            System.out.println(">> Sentencia ASIGNACIÓN válida");
        } else {
            // Si empieza con otra cosa que no sea print o un ID, es un error
            throw new RuntimeException("Error de sintaxis: Se esperaba 'print' o un identificador en la posición " + actual + " pero se encontró: " + peek().getLexema());
        }
    }

    // Regla: Expr -> Term { ('+' | '-') Term } [cite: 70]
    private void expresion() {
        termino();

        while (match(TipoToken.OPERADOR, "+") || match(TipoToken.OPERADOR, "-")) {
            termino(); // Analizamos el siguiente término tras el operador
        }
    }

    // Regla: Term -> Factor { ('*' | '/') Factor } [cite: 71]
    private void termino() {
        factor();

        while (match(TipoToken.OPERADOR, "*") || match(TipoToken.OPERADOR, "/")) {
            factor(); // Analizamos el siguiente factor tras el operador
        }
    }

    // Regla: Factor -> ID | NUM | '(' Expr ')' [cite: 72]
    private void factor() {
        if (match(TipoToken.LITERAL_NUMERICO)) {
            return; // Es un número
        }

        if (match(TipoToken.IDENTIFICADOR)) {
            return; // Es una variable
        }

        if (match(TipoToken.DELIMITADOR, "(")) {
            expresion(); // Paréntesis abierto, esperamos expresión
            consumir(TipoToken.DELIMITADOR, ")"); // Y que se cierre
            return;
        }

        throw new RuntimeException("Error de sintaxis: Se esperaba un número, variable o paréntesis, pero se encontró: " + peek().getLexema());
    }

    // --- MÉTODOS DE RECUPERACIÓN DE ERRORES (Panic Mode) ---

    // Este método salta tokens hasta encontrar un punto de sincronización seguro
    private void sincronizar() {
        advance(); // Consumimos el token que causó el error

        while (!isAtEnd()) {
            // Si acabamos de pasar un punto y coma, es un buen lugar para reiniciar
            if (previo().getLexema().equals(";")) return;

            // Si el siguiente token parece el inicio de una nueva sentencia, paramos
            Token token = peek();
            if (token.getTipo() == TipoToken.PALABRA_CLAVE &&
                    (token.getLexema().equals("print") || token.getLexema().equals("if") || token.getLexema().equals("int"))) {
                return;
            }

            // Si es un identificador, podría ser el inicio de una asignación x = ...
            // Asumimos que es seguro intentar analizar de nuevo
            if (token.getTipo() == TipoToken.IDENTIFICADOR) {
                return;
            }

            advance(); // Seguimos descartando basura hasta encontrar algo coherente
        }
    }

    // --- MÉTODOS AUXILIARES ---

    // Verifica si el token actual es del tipo esperado y avanza
    private boolean match(TipoToken tipo) {
        if (check(tipo)) {
            advance();
            return true;
        }
        return false;
    }

    // Sobrecarga para verificar tipo y lexema específico (ej: operador "+")
    private boolean match(TipoToken tipo, String lexema) {
        if (check(tipo) && peek().getLexema().equals(lexema)) {
            advance();
            return true;
        }
        return false;
    }

    // Obliga a que el token sea el esperado, si no lanza error (p.ej: falta punto y coma)
    private Token consumir(TipoToken tipo, String lexemaEsperado) {
        if (check(tipo) && peek().getLexema().equals(lexemaEsperado)) {
            return advance();
        }
        // Error específico
        throw new RuntimeException("Error de sintaxis: Se esperaba '" + lexemaEsperado + "' pero se encontró '" + peek().getLexema() + "'");
    }

    private boolean check(TipoToken tipo) {
        if (isAtEnd()) return false;
        return peek().getTipo() == tipo;
    }

    private Token advance() {
        if (!isAtEnd()) actual++;
        return tokens.get(actual - 1);
    }

    private boolean isAtEnd() {
        return actual >= tokens.size();
    }

    private Token peek() {
        if (isAtEnd()) return new Token(TipoToken.DELIMITADOR, ""); // Token dummy al final
        return tokens.get(actual);
    }

    private Token previo() {
        return tokens.get(actual - 1);
    }
}