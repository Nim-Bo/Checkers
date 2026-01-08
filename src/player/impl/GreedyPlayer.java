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

        Movement bestMove = moves.get(0);
        int maxCaptured = -1;

        for (Movement move : moves) {
            int captured = move.isJump() ? 1 : 0;
            if (captured > maxCaptured) {
                maxCaptured = captured;
                bestMove = move;
            }
        }
        return bestMove;
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
