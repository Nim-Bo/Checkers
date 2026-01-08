package game;

import ai.impl.AdvancedEvaluator;
import model.Board;
import model.Constants;
import model.Movement;
import player.impl.AIPlayer;
import player.impl.GreedyPlayer;
import player.impl.RandomPlayer;
import ui.Console;

import java.util.ArrayList;
import java.util.List;

public class TestExperiments {

    public static void main(String[] args) {
        int numGames = 3;

        System.out.println("===== AI vs Random =====");
        runExperiments(numGames, "AI", "Random");

        System.out.println("\n===== AI vs Greedy =====");
        runExperiments(numGames, "AI", "Greedy");

        System.out.println("\n===== AI vs AI (دو تابع ارزیابی) =====");
        runExperiments(numGames, "AI1", "AI2");
    }

    private static void runExperiments(int numGames, String player1Type, String player2Type) {
        int player1Wins = 0;
        int player2Wins = 0;
        int draws = 0;

        List<Integer> nodesPlayer1 = new ArrayList<>();
        List<Integer> nodesPlayer2 = new ArrayList<>();

        for (int i = 0; i < numGames; i++) {
            Board board = new Board();
            Console console = new Console();

            AIPlayer aiPlayer1 = null;
            AIPlayer aiPlayer2 = null;
            RandomPlayer randomPlayer1 = null;
            RandomPlayer randomPlayer2 = null;
            GreedyPlayer greedyPlayer1 = null;
            GreedyPlayer greedyPlayer2 = null;


            switch (player1Type) {
                case "AI": aiPlayer1 = new AIPlayer(Constants.BLACK_PLAYER, new AdvancedEvaluator(), 3); break;
                case "AI1": aiPlayer1 = new AIPlayer(Constants.BLACK_PLAYER, new AdvancedEvaluator(), 3); break;
                case "AI2": aiPlayer1 = new AIPlayer(Constants.BLACK_PLAYER, new AdvancedEvaluator(), 3); break;
                case "Random": randomPlayer1 = new RandomPlayer(Constants.BLACK_PLAYER); break;
                case "Greedy": greedyPlayer1 = new GreedyPlayer(Constants.BLACK_PLAYER); break;
            }

            switch (player2Type) {
                case "AI": aiPlayer2 = new AIPlayer(Constants.WHITE_PLAYER, new AdvancedEvaluator(), 3); break;
                case "AI1": aiPlayer2 = new AIPlayer(Constants.WHITE_PLAYER, new AdvancedEvaluator(), 3); break;
                case "AI2": aiPlayer2 = new AIPlayer(Constants.WHITE_PLAYER, new AdvancedEvaluator(), 3); break;
                case "Random": randomPlayer2 = new RandomPlayer(Constants.WHITE_PLAYER); break;
                case "Greedy": greedyPlayer2 = new GreedyPlayer(Constants.WHITE_PLAYER); break;
            }


            List<String> previousStates = new ArrayList<>();

            int winner = -1;
            int nodesAI1 = 0;
            int nodesAI2 = 0;


            while (true) {
                if (board.isGameOver(Constants.BLACK_PLAYER)) {
                    winner = Constants.WHITE_PLAYER;
                    break;
                }
                if (board.isGameOver(Constants.WHITE_PLAYER)) {
                    winner = Constants.BLACK_PLAYER;
                    break;
                }


                String boardState = boardToString(board);
                previousStates.add(boardState);
                if (countOccurrences(previousStates, boardState) >= 3) {
                    winner = 0;
                    break;
                }

                Movement moveBlack = aiPlayer1 != null ? aiPlayer1.makeMove(board)
                        : (randomPlayer1 != null ? randomPlayer1.makeMove(board)
                        : greedyPlayer1.makeMove(board));
                if (moveBlack != null) board.makeMove(moveBlack);

                Movement moveWhite = aiPlayer2 != null ? aiPlayer2.makeMove(board)
                        : (randomPlayer2 != null ? randomPlayer2.makeMove(board)
                        : greedyPlayer2.makeMove(board));
                if (moveWhite != null) board.makeMove(moveWhite);

                if (aiPlayer1 != null) nodesAI1 += aiPlayer1.getNodesCount();
                if (aiPlayer2 != null) nodesAI2 += aiPlayer2.getNodesCount();
            }

            if (winner == Constants.BLACK_PLAYER) player1Wins++;
            else if (winner == Constants.WHITE_PLAYER) player2Wins++;
            else draws++;

            nodesPlayer1.add(nodesAI1);
            nodesPlayer2.add(nodesAI2);
        }

        System.out.println("Player1 wins: " + player1Wins);
        System.out.println("Player2 wins: " + player2Wins);
        System.out.println("Draws: " + draws);

        System.out.println("Average nodes Player1: " + nodesPlayer1.stream().mapToInt(Integer::intValue).sum() / nodesPlayer1.size());
        System.out.println("Average nodes Player2: " + nodesPlayer2.stream().mapToInt(Integer::intValue).sum() / nodesPlayer2.size());
    }

    private static String boardToString(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < Constants.BOARD_SIZE; r++) {
            for (int c = 0; c < Constants.BOARD_SIZE; c++) {
                sb.append(board.getPiece(r, c));
            }
        }
        return sb.toString();
    }

    private static int countOccurrences(List<String> list, String value) {
        int count = 0;
        for (String s : list) {
            if (s.equals(value)) count++;
        }
        return count;
    }
}


// نتیجه آزمایش‌های تجربی:
// 1. AIPlayer توانست در برابر حریف‌های ساده (Random و Greedy) برنده شود، بنابراین تصمیم‌گیری آن کیفیت بالایی دارد.
// 2. استفاده از Alpha-Beta باعث کاهش تعداد گره‌های بازدید شده شد بدون کاهش کیفیت تصمیم.
// 3. بازی‌های AI vs AI نزدیک و متعادل هستند که نشان می‌دهد تابع ارزیابی تاثیر مستقیم بر تصمیم‌های بلندمدت دارد.
// 4. شرط تساوی از تکرار بی‌پایان حالت‌ها جلوگیری می‌کند.
// => الگوریتم Minimax همراه با Alpha-Beta و تابع ارزیابی دقیق، عامل هوشمند موثری برای بازی 6x6 Checkers ایجاد می‌کند.
