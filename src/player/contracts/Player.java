package player.contracts;

import model.Board;
import model.Movement;

public interface Player {
    Movement makeMove(Board board);
    int getColor();
    boolean isHuman();
}