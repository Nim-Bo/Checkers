package player.impl;

import model.Board;
import model.Movement;
import player.contracts.Player;
import ui.Console;

import java.util.List;

public class HumanPlayer implements Player {
    private int color;
    private Console console;

    public HumanPlayer(int color, Console console) {
        this.color = color;
        this.console = console;
    }

    @Override
    public Movement makeMove(Board board) {
        List<Movement> legalMoves = board.getLegalMoves(color);

        if (legalMoves.isEmpty()) {
            return null;
        }

        while (true) {
            console.showMessage("Your turn (" + (color == 1 ? "Black" : "White") + "). Available moves: " + legalMoves.size());
            Movement move = console.getMoveInput();

            if (move != null) {
                for (Movement legal : legalMoves) {
                    if (legal.getFromRow() == move.getFromRow() &&
                            legal.getFromCol() == move.getFromCol() &&
                            legal.getToRow() == move.getToRow() &&
                            legal.getToCol() == move.getToCol()) {
                        return legal;
                    }
                }
            }

            console.showMessage("Invalid move! You must choose from legal moves (mandatory jumps applied).");
            for(Movement m : legalMoves) {
                System.out.println("Hint: " + m);
            }
        }
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
