import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {
    BST<ElementoCatalogo> catalogo = new BST<>();
    private TextArea areaOutput;

    @Override
    public void start(Stage primaryStage) {
        catalogo.caricaCSV();
        Label titolo = new Label("Catalogo Multimediale");
        titolo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button btnAggiungi = new Button("Aggiungi Elemento");
        Button btnRicerca = new Button("Ricerca Elemento");
        Button btnElimina = new Button("Elimina Elemento");
        Button btnStampa = new Button("Stampa nel Terminale");

        btnAggiungi.setMaxWidth(Double.MAX_VALUE);
        btnRicerca.setMaxWidth(Double.MAX_VALUE);
        btnElimina.setMaxWidth(Double.MAX_VALUE);
        btnStampa.setMaxWidth(Double.MAX_VALUE);

        areaOutput = new TextArea();
        areaOutput.setEditable(false);
        areaOutput.setPrefHeight(250);

        btnAggiungi.setOnAction(e -> aggiungiElemento());
        btnRicerca.setOnAction(e -> ricercaElemento());
        btnElimina.setOnAction(e -> eliminaElemento());
        btnStampa.setOnAction(e -> stampaCatalogo());

        VBox menuBox = new VBox(15, titolo, btnAggiungi, btnRicerca, btnElimina, btnStampa);
        menuBox.setPadding(new Insets(20));
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setPrefWidth(250);

        BorderPane root = new BorderPane();
        root.setTop(titolo);
        root.setLeft(menuBox);
        root.setCenter(areaOutput);
        BorderPane.setAlignment(titolo, Pos.CENTER);
        BorderPane.setMargin(titolo, new Insets(20));

        Scene scene = new Scene(root, 800, 450);
        primaryStage.setTitle("Gestione Catalogo");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            catalogo.salvaCsv();
        });
        primaryStage.show();
    }

    private void aggiungiElemento() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Aggiungi al Catalogo");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll("Libro", "Film", "Videogioco");
        comboTipo.setValue("Libro");

        TextField txtId = new TextField();
        TextField txtTitolo = new TextField();
        TextField txtAnno = new TextField();

        Label lblExtra = new Label("Autore:");
        TextField txtExtra = new TextField();

        comboTipo.setOnAction(e -> {
            switch (comboTipo.getValue()) {
                case "Libro": lblExtra.setText("Autore:"); break;
                case "Film": lblExtra.setText("Regista:"); break;
                case "Videogioco": lblExtra.setText("Piattaforma:"); break;
            }
        });

        grid.add(new Label("Tipo:"), 0, 0);
        grid.add(comboTipo, 1, 0);
        grid.add(new Label("ID:"), 0, 1);
        grid.add(txtId, 1, 1);
        grid.add(new Label("Titolo:"), 0, 2);
        grid.add(txtTitolo, 1, 2);
        grid.add(new Label("Anno:"), 0, 3);
        grid.add(txtAnno, 1, 3);
        grid.add(lblExtra, 0, 4);
        grid.add(txtExtra, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    int anno = Integer.parseInt(txtAnno.getText());
                    String titolo = txtTitolo.getText();
                    String extra = txtExtra.getText();


                    if (catalogo.cerca(new ElementoCatalogo("", 0, id)) != null) {
                        areaOutput.appendText("Errore: Un elemento con ID " + id + " è già presente!\n\n");
                        return;
                    }
                    ElementoCatalogo nuovoElemento = null;

                    switch (comboTipo.getValue()) {
                        case "Libro":
                            nuovoElemento = new Libro(titolo, anno, id, extra);
                            break;
                        case "Film":
                            nuovoElemento = new Film(titolo, anno, id, extra);
                            break;
                        case "Videogioco":
                            nuovoElemento = new Videogioco(titolo, anno, id, extra);
                            break;
                    }
                    catalogo.inserisci(nuovoElemento);
                    areaOutput.appendText("Elemento inserito con successo:\n" + nuovoElemento + "\n\n");

                } catch (NumberFormatException e) {
                    areaOutput.appendText("Errore: inserisci un ID numerico valido.\n\n");
                }
            }
        });
    }

    private void ricercaElemento() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ricerca");
        dialog.setContentText("Inserisci l'ID da cercare:");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Nodo<ElementoCatalogo> nodo = catalogo.cerca(new ElementoCatalogo("", 0, id));

                if (nodo != null) {
                    areaOutput.appendText("Trovato: " + nodo.getValore() + "\n\n");
                } else {
                    areaOutput.appendText("Nessun elemento trovato con ID: " + id + "\n\n");
                }
            } catch (NumberFormatException e) {
                areaOutput.appendText("Errore: inserisci un ID numerico valido.\n\n");
            }
        });
    }

    private void eliminaElemento() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Elimina");
        dialog.setHeaderText("Eliminazione elemento");
        dialog.setContentText("Inserisci l'ID da eliminare:");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Nodo<ElementoCatalogo> nodo = catalogo.cerca(new ElementoCatalogo("", 0, id));
                if (nodo != null) {
                    catalogo.elimina(new ElementoCatalogo("", 0, id));
                    areaOutput.appendText("Eliminato con successo:\n" + nodo.getValore() + "\n\n");
                } else {
                    areaOutput.appendText("Impossibile eliminare: nessun elemento con ID " + id + "\n\n");
                }
            } catch (NumberFormatException e) {
                areaOutput.appendText("Errore: inserisci un ID numerico valido.\n\n");
            }
        });
    }
    private void stampaCatalogo() {
        areaOutput.appendText("stampa albero del catalogo\n\n");
        areaOutput.appendText(catalogo.getStampaGrafica());
    }

    public static void main(String[] args) {
        launch(args);
    }
}