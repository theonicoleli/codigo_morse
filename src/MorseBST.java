import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class MorseBST {
    private final Node root = new Node();
    private final Map<Character, String> encodingMap = new HashMap<>();

    private static final String[][] MAPPINGS = {
            {"A", ".-"}, {"B", "-..."}, {"C", "-.-."}, {"D", "-.."},
            {"E", "."},  {"F", "..-."}, {"G", "--."},  {"H", "...."},
            {"I", ".."}, {"J", ".---"}, {"K", "-.-"},  {"L", ".-.."},
            {"M", "--"}, {"N", "-."},   {"O", "---"},  {"P", ".--."},
            {"Q", "--.-"},{"R", ".-."}, {"S", "..."},  {"T", "-"},
            {"U", "..-"},{"V", "...-"}, {"W", ".--"},  {"X", "-..-"},
            {"Y", "-.--"},{"Z", "--.."}
    };

    public MorseBST() {
        for (String[] m : MAPPINGS) {
            char letter = m[0].charAt(0);
            String code = m[1];
            insert(letter, code);
            encodingMap.put(letter, code);
        }
    }

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

    public String decode(String seq) {
        StringBuilder sb = new StringBuilder();
        for (String tok : seq.trim().split("\\s+")) {
            Node cur = root;
            for (char c : tok.toCharArray()) {
                cur = (c == '.' ? cur.left : cur.right);
                if (cur == null) break;
            }
            sb.append(cur != null && cur.letter != '\0' ? cur.letter : '?');
        }
        return sb.toString();
    }

    public String encode(String word) {
        StringBuilder sb = new StringBuilder();
        for (char ch : word.toUpperCase().toCharArray()) {
            String code = encodingMap.get(ch);
            sb.append(code != null ? code : "?").append(' ');
        }
        return sb.toString().trim();
    }

    public int getHeight() {
        return height(root);
    }

    private int height(Node n) {
        return n == null ? 0 : 1 + Math.max(height(n.left), height(n.right));
    }

    public void drawTree(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4);
    }

    private void drawNode(GraphicsContext gc, Node node, double x, double y, double xOff) {
        if (node == null) return;
        gc.strokeOval(x - 15, y - 15, 30, 30);
        if (node.letter != '\0') gc.strokeText("" + node.letter, x - 5, y + 5);
        if (node.left != null) {
            double nx = x - xOff, ny = y + 120;
            gc.strokeLine(x, y + 15, nx, ny - 15);
            drawNode(gc, node.left, nx, ny, xOff / 2);
        }
        if (node.right != null) {
            double nx = x + xOff, ny = y + 120;
            gc.strokeLine(x, y + 15, nx, ny - 15);
            drawNode(gc, node.right, nx, ny, xOff / 2);
        }
    }
}
