package player.impl;

import model.Board;
import model.Constants;
import model.Movement;
import player.contracts.Player;

import java.util.List;

public class GreedyPlayer extends Constants implements Player {
    private int color;

    public GreedyPlayer(int color) {
        this.color = color;
    }

    @Override
    public Movement makeMove(Board board) {
        List<Movement> moves = board.getLegalMoves(color);
        if (moves.isEmpty()) return null;

        for (Movement move : moves) {
            if (move.isJump()) {
                return move;
            }
        }

        return moves.getFirst();
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public boolean isHuman() {
        return false;
    }
}
