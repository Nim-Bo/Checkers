package player.impl;

import model.Board;
import model.Movement;
import player.contracts.Player;

import java.util.List;
import java.util.Random;

public class RandomPlayer implements Player {

    private int color;
    private Random random = new Random();

    public RandomPlayer(int color) {
        this.color = color;
    }

    @Override
    public Movement makeMove(Board board) {
        List<Movement> moves = board.getLegalMoves(color);
        if (moves.isEmpty()) return null;
        return moves.get(random.nextInt(moves.size()));
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
