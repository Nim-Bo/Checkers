package ai.contracts;

import model.Board;

public interface Evaluator {
    int evaluate(Board board, int playerColor);
}