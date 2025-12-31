import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ScannerLexico lexico = new ScannerLexico();

        System.out.println("Introduce tu código (pulsa Enter dos veces para terminar):");

        // --- CAMBIO: Leer múltiples líneas ---
        StringBuilder codigoCompleto = new StringBuilder();
        while (entrada.hasNextLine()) {
            String linea = entrada.nextLine();
            if (linea.isEmpty()) break; // Termina si la línea está vacía
            codigoCompleto.append(linea).append(" "); // Añadimos espacio para evitar pegotes
        }
        // -------------------------------------

        // 1. Análisis Léxico
        List<Token> tokens = lexico.analizar(codigoCompleto.toString());

        System.out.println("\n--- 1. ANÁLISIS LÉXICO (Tokens) ---");
        for (Token t : tokens) {
            System.out.println(t);
        }

        // 2. Análisis Sintáctico
        System.out.println("\n--- 2. ANÁLISIS SINTÁCTICO ---");
        Parser parser = new Parser(tokens); // No necesitamos try-catch aquí, el parser los gestionará dentro
        parser.parse();

        entrada.close();
    }
}