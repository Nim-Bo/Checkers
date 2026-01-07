package game;

import model.Board;
import model.Constants;
import model.Movement;
import player.contracts.Player;
import ui.Console;

public class GameManager extends Constants {
    private Board board;
    private Player playerBlack;
    private Player playerWhite;
    private Console ui;

    public GameManager(Board board, Player playerBlack, Player playerWhite, Console ui) {
        this.board = board;
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.ui = ui;
    }

    public void startGame() {
        Player currentPlayer = playerBlack;

        while (true) {
            ui.printBoard(board);

            if (board.isGameOver(currentPlayer.getColor())) {
                handleGameOver(currentPlayer);
                break;
            }

            ui.showMessage("Turn: " + (currentPlayer.getColor() == BLACK ? "Black" : "White"));

            Movement move = currentPlayer.makeMove(board);

            board.makeMove(move);

            ui.showMessage("Moved: " + move);

            currentPlayer = (currentPlayer == playerBlack) ? playerWhite : playerBlack;
        }
    }

    private void handleGameOver(Player loser) {
        ui.printBoard(board);
        ui.showMessage("GAME OVER!");

        String winner = (loser.getColor() == BLACK) ? "White" : "Black";
        ui.showMessage("Winner is: " + winner);
    }
}
