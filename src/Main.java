import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Morse – Decodificar ou Codificar");
        MorseBST bst = new MorseBST();

        List<String> options = Arrays.asList("Decodificar", "Codificar");
        ChoiceDialog<String> choice = new ChoiceDialog<>(options.get(0), options);
        choice.setTitle("Escolha de Função");
        choice.setHeaderText("Selecione a operação:");
        choice.setContentText("Operação:");
        Optional<String> opt = choice.showAndWait();
        if (!opt.isPresent()) {
            primaryStage.close();
            return;
        }

        if (opt.get().equals("Decodificar")) {
            TextInputDialog dlg = new TextInputDialog();
            dlg.setTitle("Decodificar Morse");
            dlg.setHeaderText("Digite código Morse (. e -, espaço entre letras):");
            dlg.setContentText("Código:");
            dlg.showAndWait().ifPresent(seq -> {
                String decoded = bst.decode(seq);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Decodificado");
                a.setHeaderText("Texto decodificado:");
                a.setContentText(decoded);
                a.showAndWait();
                System.out.println("Decodificado: " + decoded);
            });
        } else {
            TextInputDialog dlg = new TextInputDialog();
            dlg.setTitle("Codificar em Morse");
            dlg.setHeaderText("Digite palavra para converter em Morse:");
            dlg.setContentText("Texto:");
            dlg.showAndWait().ifPresent(txt -> {
                String morse = bst.encode(txt);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Codificado");
                a.setHeaderText("Código Morse:");
                a.setContentText(morse);
                a.showAndWait();
                System.out.println("Codificado: " + morse);
            });
        }

        int h = bst.getHeight();
        int w = (int) Math.pow(2, h) * 40;
        int ht = 100 + h * 100;
        Canvas canvas = new Canvas(w, ht);
        bst.drawTree(canvas);
        Group root = new Group(canvas);
        Scene scene = new Scene(root, w, ht);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
