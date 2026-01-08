package ai.impl;

import ai.contracts.Evaluator;
import model.Board;
import model.Constants;

public class SimpleEvaluator extends Constants implements Evaluator {

    @Override
    public int evaluate(Board board, int playerColor) {
        int blackScore = 0;
        int whiteScore = 0;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int piece = board.getPiece(row, col);

                if (piece == BLACK) blackScore += 10;
                else if (piece == BLACK_KING) blackScore += 15;
                else if (piece == WHITE) whiteScore += 10;
                else if (piece == WHITE_KING) whiteScore += 15;
            }
        }

        return playerColor == BLACK_PLAYER
                ? blackScore - whiteScore
                : whiteScore - blackScore;
    }
}