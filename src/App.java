import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class App {
    public static void main(String[] args) throws Exception {

        String arquivo = gerarString();

        // Remover comentario
        arquivo = arquivo.replaceAll("(?s:/\\*.*?\\*/)|//.*", "");

        // Remover espaço
        arquivo = arquivo.replaceAll("(?s)\\s+|/\\*.*?\\*/|//[^\\r\\n]*", " ");

        // Remover Espaço
        arquivo = arquivo
                .replaceAll("(?<=.)(\\,|\\\"|\\(|\\)|\\'|\\;|\\.|\\{|\\}|\\:|\\++|\\-|\\!|\\?|\\[|\\])(|[a-z])", " ");

        // Transformar em token
        StringTokenizer defaultTokenizer = new StringTokenizer(arquivo);

        Integer tamanho = defaultTokenizer.countTokens();
        Integer cont = 0;

        // Palavras reservadas do JavaScript
        String[] operadores = { "+", "-", "*", "/", "=" };
        String[] palavrasReservadas = { "abstract", "arguments", "boolean", "break", "byte", "case", "catch", "char",
                "class", "const", "continue", "debugger", "default", "delete", "do", "double", "else", "enum", "eval",
                "export", "extends", "false", "final", "finally", "float", "for", "function", "goto", "if",
                "implements", "import", "in", "instanceof", "int", "interface", "let", "long", "native", "new", "null",
                "package", "private", "protected", "public", "return", "short", "static", "super", "switch",
                "synchronized", "this", "throw", "throws", "transient", "true", "try", "typeof", "var", "void",
                "volatile", "while", "with", "yield" };

        String[] comparadores = { "==", "!=", "===", "!==", ">", "<", ">=", "<=", "!", "||", "&&", "?" };

        String stringValidada = "";
        String tipoDado = "";

        FileWriter arq = new FileWriter("E://Java//Comp//Teste//Resultado.lex");
        PrintWriter gravarArq = new PrintWriter(arq);

        while (cont < tamanho - 1) {
            stringValidada = defaultTokenizer.nextToken();
            tipoDado = verificarString(stringValidada, palavrasReservadas, comparadores, operadores);
            cont++;
            gravarArq.println(cont + " - " + stringValidada + " - " + tipoDado);
        }

        arq.close();

    }

    public static String gerarString() throws IOException {
        String textRetorno = "";
        FileInputStream stream = new FileInputStream("E://Java//Comp//Teste//Arquivo.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);
        ArrayList<String> linhas = new ArrayList<String>();
        String linha = "";

        while (linha != null) {
            linha = br.readLine();
            textRetorno = textRetorno + linha + "\n";
        }
        return textRetorno;
    }

    public static String verificarString(String texto, String[] palavrasReservadas, String[] comparadores,
            String[] operadores) {

        for (String a : palavrasReservadas) {
            if (a.equals(texto))
                return "palavra reservada";
        }

        for (String a : comparadores) {
            if (a.equals(texto))
                return "comparador";
        }

        for (String a : operadores) {
            if (a.equals(texto))
                return "operador";
        }

        if (texto.matches("[0-9]"))
            return "inteiro";

        if (texto.matches("[0-9]\\.[0-9]"))
            return "float";

        if (texto.matches("(true|false)"))
            return "boolean";

        if (texto.matches(
                "(?i)string|int|array|date|list|JSON|float|boolean|var|null|undefined|function|bigint|char|symbol|math"))
            return "tipo-de-dado";

        if (texto.matches(".$"))
            return "char";

        if (texto.matches("[a-z][a-z]+[A-Z][a-z]+"))
            return "funcao";

        if (texto.matches("[a-z][A-Z][a-z]+"))
            return "variavel";

        return "string";
    }
}
