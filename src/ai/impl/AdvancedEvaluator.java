package ai.impl;

import ai.contracts.Evaluator;
import model.Board;
import model.Constants;

public class AdvancedEvaluator extends Constants implements Evaluator {

    @Override
    public int evaluate(Board board, int playerColor) {
        int blackScore = 0;
        int whiteScore = 0;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int piece = board.getPiece(row, col);
                if (piece == EMPTY) continue;


            }
        }

        return blackScore - whiteScore;
    }
}
