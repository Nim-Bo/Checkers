package player.impl;

import ai.contracts.Evaluator;
import model.Board;
import model.Constants;
import model.Movement;
import player.contracts.Player;

import java.util.*;

public class AIPlayer extends Constants implements Player {
    private int color;
    private Evaluator evaluator;
    private int maxDepth;
    private int nodesCount;

    // ==== برای مرحله ششم: یادگیری از تجربه ====
    private Map<String, Integer> experienceTable = new HashMap<>(); // ذخیره ارزش تجربه‌ها
    private List<String> currentGameStates = new ArrayList<>();     // وضعیت‌های بازی فعلی

    public AIPlayer(int color, Evaluator evaluator, int maxDepth) {
        this.color = color;
        this.evaluator = evaluator;
        this.maxDepth = maxDepth;
    }

    @Override
    public Movement makeMove(Board board) {
        nodesCount = 0;
        long startTime = System.currentTimeMillis();

        // ذخیره وضعیت فعلی برای تجربه
        currentGameStates.add(boardToString(board));

        Movement bestMove = null;
        int bestValue;

        boolean isMaximizing = (color == BLACK_PLAYER);

        if (isMaximizing) {
            bestValue = Integer.MIN_VALUE;
        } else {
            bestValue = Integer.MAX_VALUE;
        }

        List<Movement> moves = board.getLegalMoves(color);

        for (Movement move : moves) {
            Board clonedBoard = board.copy();
            clonedBoard.makeMove(move);

            int value = minimax(clonedBoard, maxDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, !isMaximizing);

            if (isMaximizing) {
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            } else {
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("AI (" + (color == BLACK_PLAYER ? "Black" : "White") + ") selected move: " + bestMove);
        System.out.println("Nodes visited: " + nodesCount + ", Time: " + duration + "ms");

        return bestMove;
    }

    // ==== Minimax با Alpha-Beta و تجربه ====
    private int minimax(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        nodesCount++;

        if (depth == 0 || board.isGameOver(isMaximizing ? BLACK_PLAYER : WHITE_PLAYER)) {
            return evaluateWithExperience(board);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            List<Movement> moves = board.getLegalMoves(BLACK_PLAYER);

            for (Movement move : moves) {
                Board newBoard = board.copy();
                newBoard.makeMove(move);

                int eval = minimax(newBoard, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);

                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;
            List<Movement> moves = board.getLegalMoves(WHITE_PLAYER);

            for (Movement move : moves) {
                Board newBoard = board.copy();
                newBoard.makeMove(move);

                int eval = minimax(newBoard, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);

                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    // ==== تابع ارزیابی با تجربه ====
    private int evaluateWithExperience(Board board) {
        int eval = evaluator.evaluate(board, color); // ارزیابی معمولی
        String key = boardToString(board);
        if (experienceTable.containsKey(key)) {
            eval += experienceTable.get(key); // اضافه کردن ارزش تجربه
        }
        return eval;
    }

    // ==== به‌روزرسانی تجربه بعد از بازی ====
    public void updateExperience(int winner) {
        int reward = (winner == color) ? 10 : (winner == 0 ? 0 : -10);
        for (String state : currentGameStates) {
            experienceTable.put(state, experienceTable.getOrDefault(state, 0) + reward);
        }
        currentGameStates.clear();
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    public int getNodesCount() {
        return nodesCount;
    }

    // ==== تبدیل وضعیت صفحه به رشته برای ذخیره تجربه ====
    private String boardToString(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                sb.append(board.getPiece(r, c));
            }
        }
        return sb.toString();
    }
}
