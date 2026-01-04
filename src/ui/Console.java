package ui;

import model.Board;
import model.Constants;
import model.Movement;

import java.util.Scanner;

public class Console extends Constants {
    private Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    public void printBoard(Board board) {
        System.out.println("\n  0 1 2 3 4 5");
        System.out.println(" +-----------+");

        for (int row = 0; row <  BOARD_SIZE; row++) {
            System.out.print(row + "|");
            for (int col = 0; col <  Constants.BOARD_SIZE; col++) {
                int piece = board.getPiece(row, col);
                System.out.print(getPieceChar(piece) + "|");
            }
            System.out.println("\n +-----------+");
        }
    }

    private String getPieceChar(int piece) {
        switch (piece) {
            case BLACK: return "b";
            case WHITE: return "w";
            case BLACK_KING: return "B";
            case WHITE_KING: return "W";
            case EMPTY: return " ";
            default: return "?";
        }
    }

    public Movement getMoveInput() {
        System.out.print("Enter move (row1 col1 row2 col2): ");
        try {
            int r1 = scanner.nextInt();
            int c1 = scanner.nextInt();
            int r2 = scanner.nextInt();
            int c2 = scanner.nextInt();
            return new Movement(r1, c1, r2, c2);
        } catch (Exception e) {
            scanner.nextLine();
            return null;
        }
    }

    public void showMessage(String message) {
        System.out.println(">> " + message);
    }
}