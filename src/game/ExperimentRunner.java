package game;

import ai.contracts.Evaluator;
import model.Board;
import model.Constants;
import player.contracts.Player;

public class ExperimentRunner extends Constants {

    public static int runSingleGame(Player black, Player white) {
        Board board = new Board();
        Player current = black;

        while (true) {
            if (board.isGameOver(current.getColor())) {
                return (current == black) ? WHITE_PLAYER : BLACK_PLAYER;
            }

            board.makeMove(current.makeMove(board));
            current = (current == black) ? white : black;
        }
    }

    public static void runMultipleGames(
            Player black, Player white, int games) {

        int blackWins = 0, whiteWins = 0;

        for (int i = 0; i < games; i++) {
            int winner = runSingleGame(black, white);
            if (winner == BLACK_PLAYER) blackWins++;
            else whiteWins++;
        }

        System.out.println("Results after " + games + " games:");
        System.out.println("Black wins: " + blackWins);
        System.out.println("White wins: " + whiteWins);
    }
}
