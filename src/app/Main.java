package app;

import ai.contracts.Evaluator;
import ai.impl.AdvancedEvaluator;
import ai.impl.SimpleEvaluator;
import game.GameManager;
import model.Board;
import model.Constants;
import player.contracts.Player;
import player.impl.AIPlayer;
import player.impl.HumanPlayer;
import ui.Console;
import game.ExperimentRunner;
import player.impl.RandomPlayer;

import java.util.Scanner;

public class Main extends Constants {
    public static void main(String[] args) {
        Console ui = new Console();
        Board board = new Board();

        Scanner scanner = new Scanner(System.in);

        ui.showMessage("Select Difficulty:");
        ui.showMessage("1. Easy (Simple Evaluation)");
        ui.showMessage("2. Hard (Advanced Evaluation)");
        int diff = scanner.nextInt();

        Evaluator evaluator;
        if (diff == 1) {
            evaluator = new SimpleEvaluator();
        } else {
            evaluator = new AdvancedEvaluator();
        }

        ui.showMessage("Select Game Mode:");
        ui.showMessage("1. Human vs Human");
        ui.showMessage("2. Human (Black) vs AI (White)");
        ui.showMessage("3. AI (Black) vs Human (White)");
        ui.showMessage("4. AI vs AI");

        int choice = scanner.nextInt();

        Player p1, p2;
        int depth = 5;

        switch (choice) {
            case 1:
                p1 = new HumanPlayer(BLACK_PLAYER, ui);
                p2 = new HumanPlayer(WHITE_PLAYER, ui);
                break;
            case 2:
                p1 = new HumanPlayer(BLACK_PLAYER, ui);
                p2 = new AIPlayer(WHITE_PLAYER, evaluator, depth);
                break;
            case 3:
                p1 = new AIPlayer(BLACK_PLAYER, evaluator, depth);
                p2 = new HumanPlayer(WHITE_PLAYER, ui);
                break;
            case 4:
                p1 = new AIPlayer(BLACK_PLAYER, evaluator, depth);
                p2 = new AIPlayer(WHITE_PLAYER, evaluator, depth);
                break;
            default:
                ui.showMessage("Invalid choice, defaulting to Human vs AI");
                p1 = new HumanPlayer(BLACK_PLAYER, ui);
                p2 = new AIPlayer(WHITE_PLAYER, evaluator, depth);
        }

        GameManager gameManager = new GameManager(board, p1, p2, ui);
        gameManager.startGame();
    }
}
