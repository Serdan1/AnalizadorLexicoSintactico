import java.util.ArrayList;
import java.util.List;

public class ScannerLexico {

    public List<Token> analizar(String codigo) {
        List<Token> tokens = new ArrayList<>();

        // 1. Pre-procesamiento: Añadir espacios para separar bien
        codigo = codigo.replaceAll(";", " ; ");
        codigo = codigo.replaceAll("\\(", " ( ");
        codigo = codigo.replaceAll("\\)", " ) ");
        codigo = codigo.replaceAll("=", " = ");
        codigo = codigo.replaceAll("\\+", " + ");
        codigo = codigo.replaceAll("-", " - ");
        codigo = codigo.replaceAll("\\*", " * ");
        codigo = codigo.replaceAll("/", " / ");

        // 2. Dividir por espacios
        String[] palabras = codigo.trim().split("\\s+");

        // 3. Clasificar cada palabra
        for (String palabra : palabras) {
            if (palabra.isEmpty()) continue;
            tokens.add(clasificarToken(palabra));
        }

        return tokens;
    }

    private Token clasificarToken(String lexema) {
        // Palabras clave
        if (lexema.equals("int") || lexema.equals("if") || lexema.equals("print")) {
            return new Token(TipoToken.PALABRA_CLAVE, lexema);
        }

        // Delimitadores
        if (lexema.equals("(") || lexema.equals(")") || lexema.equals(";")) {
            return new Token(TipoToken.DELIMITADOR, lexema);
        }

        // Operadores
        if (lexema.equals("+") || lexema.equals("-") || lexema.equals("*") || lexema.equals("/") || lexema.equals("=")) {
            return new Token(TipoToken.OPERADOR, lexema);
        }

        // Literales Numéricos (Regex simple)
        if (lexema.matches("[0-9]+")) {
            return new Token(TipoToken.LITERAL_NUMERICO, lexema);
        }

        // Identificadores (todo lo demás)
        return new Token(TipoToken.IDENTIFICADOR, lexema);
    }
}