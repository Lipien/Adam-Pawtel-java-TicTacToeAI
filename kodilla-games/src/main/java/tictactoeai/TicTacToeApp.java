package tictactoeai;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToeApp extends Application {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private static final String TITLE = "Tic Tac Toe (JavaFX)";
    private CellContent currentPlayer = CellContent.CROSS;
    private CellContent comp = CellContent.NOUGHT;
    private Cell[][] cells = new Cell[3][3];
    private Label statusMsg = new Label(currentPlayer + "es move first");
    private RadioButton radioButton = new RadioButton("play against PC");
    private GridPane gridPane;
    private BorderPane borderPane;
    private AIPlayer aiPlayer;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        aiPlayer = new CPUplayer();

        initBoard();
        statusMsg.setFont(Font.font("Verdana", 20));
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setCenter(gridPane);
        borderPane.setStyle("-fx-padding: 10");
        borderPane.setTop(statusMsg);
        borderPane.setBottom(null);

        Button newGameButton = new Button("New GAME");
        newGameButton.setPrefSize(100, 20);
        newGameButton.setDefaultButton(true);
        newGameButton.setOnAction(event -> {
                    try {
                        initBoard();
                        currentPlayer = CellContent.CROSS;
                        comp = CellContent.NOUGHT;
                        borderPane.setCenter(gridPane);
                        statusMsg.setText(CellContent.CROSS + "es move first");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        Button exitButton = new Button("EXIT");
        exitButton.setPrefSize(100, 20);
        exitButton.setOnAction(e -> Platform.exit());

        radioButton.setSelected(true);

        VBox buttonBar = new VBox();
        buttonBar.getChildren().addAll(newGameButton, exitButton, radioButton);
        buttonBar.setPadding(new Insets(15, 12, 15, 12));
        buttonBar.setSpacing(30);

        borderPane.setRight(buttonBar);
        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initBoard() {
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
                cells[i][j].setOnMouseClicked(e -> handleClick(e));
                gridPane.add(cells[i][j], i, j);
            }
        }
    }

    private boolean hasWon(CellContent cellContent) {
        for (int i = 0; i < 3; i++)
            if (cells[i][0].getCellContent() == cellContent && cells[i][1].getCellContent() == cellContent
                    && cells[i][2].getCellContent() == cellContent) {
                cells[i][0].setStyle("-fx-background-color: darkgray;");
                cells[i][1].setStyle("-fx-background-color: darkgray;");
                cells[i][2].setStyle("-fx-background-color: darkgray;");
                return true;
            }
        for (int i = 0; i < 3; i++) {
            if (cells[0][i].getCellContent() == cellContent && cells[1][i].getCellContent() == cellContent
                    && cells[2][i].getCellContent() == cellContent) {
                cells[0][i].setStyle("-fx-background-color: darkgray;");
                cells[1][i].setStyle("-fx-background-color: darkgray;");
                cells[2][i].setStyle("-fx-background-color: darkgray;");
                return true;
            }
        }
        if (cells[0][0].getCellContent() == cellContent && cells[1][1].getCellContent() == cellContent
                && cells[2][2].getCellContent() == cellContent) {
            cells[0][0].setStyle("-fx-background-color: darkgray;");
            cells[1][1].setStyle("-fx-background-color: darkgray;");
            cells[2][2].setStyle("-fx-background-color: darkgray;");
            return true;
        }
        if (cells[0][2].getCellContent() == cellContent && cells[1][1].getCellContent() == cellContent
                && cells[2][0].getCellContent() == cellContent) {
            cells[0][2].setStyle("-fx-background-color: darkgray;");
            cells[1][1].setStyle("-fx-background-color: darkgray;");
            cells[2][0].setStyle("-fx-background-color: darkgray;");
            return true;
        }
        return false;
    }

    public boolean isBoardFull() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)

                if (cells[i][j].getCellContent() == CellContent.EMPTY) {
                    return false;
                }
        }
        return true;
    }


    public boolean compTurn() {
        int[] move = aiPlayer.move(cells);
        int see = 0;
        while (see == 0) {
            if (move != null) {
                cells[move[0]][move[1]].setCellContent(comp);
                see = 1;
            }
            if (hasWon(comp)) {
                statusMsg.setText(comp + " wins! The game is over");
                currentPlayer = CellContent.EMPTY;
            } else if (isBoardFull()) {
                statusMsg.setText("Draw! Play one more time :-)");
            }
        }
        return false;
    }

    private void handleClick(MouseEvent e) {
        Cell cells = (Cell) e.getSource();
        if (radioButton.isSelected()) {
            if (cells.getCellContent() == CellContent.EMPTY && currentPlayer != CellContent.EMPTY) {
                cells.setCellContent(currentPlayer);
                if (hasWon(cells.getCellContent())) {
                    statusMsg.setText(currentPlayer + " wins!");
                    currentPlayer = CellContent.EMPTY;
                } else if (isBoardFull()) {
                    statusMsg.setText("Draw!");
                    currentPlayer = CellContent.EMPTY;
                } else {
                    compTurn();
                }
            }
        } else {
            if (cells.getCellContent() == CellContent.EMPTY && currentPlayer != CellContent.EMPTY) {
                cells.setCellContent(currentPlayer);
                if (hasWon(cells.getCellContent())) {
                    statusMsg.setText(currentPlayer + " wins !");
                    currentPlayer = CellContent.EMPTY;
                } else if (isBoardFull()) {
                    statusMsg.setText("Draw!");
                    currentPlayer = CellContent.EMPTY;
                } else {
                    currentPlayer = (currentPlayer == CellContent.CROSS) ? CellContent.NOUGHT : CellContent.CROSS;
                    statusMsg.setText(" Now it's " + currentPlayer + "'s move");
                }
            }
        }
    }

}