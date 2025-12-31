import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ScannerLexico lexico = new ScannerLexico();

        System.out.println("Introduce una instrucción (ej: x = 5 + 2;):");
        String linea = entrada.nextLine();

        List<Token> tokens = lexico.analizar(linea);

        System.out.println("\n--- LISTA DE TOKENS ---");
        for (Token t : tokens) {
            System.out.println(t);
        }
    }
}