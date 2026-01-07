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

                int score = 0;

                if (piece == BLACK || piece == WHITE) score += 10;
                if (piece == BLACK_KING || piece == WHITE_KING) score += 20;

                if (col == 0 || col == BOARD_SIZE - 1) {
                    score += 2;
                }

                if (piece == BLACK) {
                    score += row;

                    if (row == 0) score += 3;
                }
                if (piece == WHITE) {
                    score += (5 - row);

                    if (row == 5) score += 3;
                }

                if (piece == BLACK || piece == BLACK_KING) {
                    blackScore += score;
                } else {
                    whiteScore += score;
                }
            }
        }

        return blackScore - whiteScore;
    }
}
