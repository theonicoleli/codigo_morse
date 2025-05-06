import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class MorseBST {
    private final Node root = new Node();
    private final Map<Character, String> encodingMap = new HashMap<>();

    // relacao entre letras e morse
    private static final String[][] MAPPINGS = {
            {"A", ".-"}, {"B", "-..."}, {"C", "-.-."}, {"D", "-.."},
            {"E", "."},  {"F", "..-."}, {"G", "--."},  {"H", "...."},
            {"I", ".."}, {"J", ".---"}, {"K", "-.-"},  {"L", ".-.."},
            {"M", "--"}, {"N", "-."},   {"O", "---"},  {"P", ".--."},
            {"Q", "--.-"},{"R", ".-."}, {"S", "..."},  {"T", "-"},
            {"U", "..-"},{"V", "...-"}, {"W", ".--"},  {"X", "-..-"},
            {"Y", "-.--"},{"Z", "--.."}
    };

    // Construtor da arvore
    public MorseBST() {
        for (String[] m : MAPPINGS) {
            char letter = m[0].charAt(0);
            String code = m[1];
            insert(letter, code); // insere as letras e o seu morse
            encodingMap.put(letter, code); // Vai para o mapeamento encodingMap
        }
    }

    // Insere letras na arvore de acordo com o morse
    public void insert(char letter, String morseCode) {
        Node cur = root;
        for (char c : morseCode.toCharArray()) {
            if (c == '.') {
                if (cur.left == null) cur.left = new Node();
                cur = cur.left;
            } else {
                if (cur.right == null) cur.right = new Node();
                cur = cur.right;
            }
        }
        cur.letter = letter;
    }

    // Decodifica o morse
    public String decode(String seq) {
        StringBuilder sb = new StringBuilder();
        for (String tok : seq.trim().split("\\s+")) {
            Node cur = root;
            for (char c : tok.toCharArray()) {
                cur = (c == '.' ? cur.left : cur.right); // Move na árvore de acordo com o código Morse
                if (cur == null) break;
            }
            sb.append(cur != null && cur.letter != '\0' ? cur.letter : '?'); // Adiciona o caractere decodificado
        }
        return sb.toString();
    }

    // Codifica uma palavra para codigo morse
    public String encode(String word) {
        StringBuilder sb = new StringBuilder();
        for (char ch : word.toUpperCase().toCharArray()) {
            String code = encodingMap.get(ch); // Obtém o código Morse da letra
            sb.append(code != null ? code : "?").append(' '); // Adiciona o código Morse correspondente
        }
        return sb.toString().trim();
    }

    // calcula a altura da arvore
    public int getHeight() {
        return height(root);
    }

    // funcao recursiva para calcular a altura da arvore
    private int height(Node n) {
        return n == null ? 0 : 1 + Math.max(height(n.left), height(n.right));
    }

    // desenha a arvore no canvas
    public void drawTree(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Limpa o canvas
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4); // Desenha cada nó da árvore
    }

    // funcao para desenhar cada nó
    private void drawNode(GraphicsContext gc, Node node, double x, double y, double xOff) {
        if (node == null) return;
        gc.strokeOval(x - 15, y - 15, 30, 30); // Desenha o círculo representando o nó
        if (node.letter != '\0') gc.strokeText("" + node.letter, x - 5, y + 5); // Exibe a letra no nó
        if (node.left != null) {
            double nx = x - xOff, ny = y + 120;
            gc.strokeLine(x, y + 15, nx, ny - 15); // Desenha linha para o filho à esquerda
            drawNode(gc, node.left, nx, ny, xOff / 2); // Chamada recursiva para o filho à esquerda
        }
        if (node.right != null) {
            double nx = x + xOff, ny = y + 120;
            gc.strokeLine(x, y + 15, nx, ny - 15); // Desenha linha para o filho à direita
            drawNode(gc, node.right, nx, ny, xOff / 2); // Chamada recursiva para o filho à direita
        }
    }
}
