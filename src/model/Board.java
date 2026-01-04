package model;

import java.util.ArrayList;
import java.util.List;

public class Board extends Constants {
    private int[][] grid;

    public Board() {
        grid = new int[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 != 0) {
                    if (row < 2) {
                        grid[row][col] = BLACK;
                    } else if (row > 3) {
                        grid[row][col] = WHITE;
                    } else {
                        grid[row][col] = EMPTY;
                    }
                } else {
                    grid[row][col] = EMPTY;
                }
            }
        }
    }

    public Board copy() {
        Board newBoard = new Board();
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(this.grid[i], 0, newBoard.grid[i], 0, BOARD_SIZE);
        }
        return newBoard;
    }

    public int getPiece(int row, int col) {
        return grid[row][col];
    }

    public void makeMove(Movement move) {
        int piece = grid[move.getFromRow()][move.getFromCol()];

        grid[move.getFromRow()][move.getFromCol()] = EMPTY;

        if (move.isJump()) {
            int midRow = (move.getFromRow() + move.getToRow()) / 2;
            int midCol = (move.getFromCol() + move.getToCol()) / 2;
            grid[midRow][midCol] = EMPTY;
        }

        grid[move.getToRow()][move.getToCol()] = piece;

        if (piece == BLACK && move.getToRow() == BOARD_SIZE - 1) {
            grid[move.getToRow()][move.getToCol()] = BLACK_KING;
        } else if (piece == WHITE && move.getToRow() == 0) {
            grid[move.getToRow()][move.getToCol()] = WHITE_KING;
        }
    }

    public List<Movement> getLegalMoves(int player) {
        List<Movement> jumps = new ArrayList<>();
        List<Movement> slides = new ArrayList<>();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int piece = grid[row][col];
                if (isPlayersPiece(piece, player)) {
                    checkMovesForPiece(row, col, piece, jumps, slides);
                }
            }
        }

        if (!jumps.isEmpty()) {
            return jumps;
        }
        return slides;
    }

    private boolean isPlayersPiece(int piece, int player) {
        if (player == BLACK_PLAYER) return piece == BLACK || piece == BLACK_KING;
        return piece == WHITE || piece == WHITE_KING;
    }

    private void checkMovesForPiece(int row, int col, int piece, List<Movement> jumps, List<Movement> slides) {
        int[] rowDirs;
        if (piece == BLACK) rowDirs = new int[]{1};
        else if (piece == WHITE) rowDirs = new int[]{-1};
        else rowDirs = new int[]{-1, 1};

        int[] colDirs = {-1, 1};

        for (int rDir : rowDirs) {
            for (int cDir : colDirs) {
                int rNext = row + rDir;
                int cNext = col + cDir;

                if (isValidPos(rNext, cNext) && grid[rNext][cNext] == EMPTY) {
                    slides.add(new Movement(row, col, rNext, cNext));
                }

                int rJump = row + (rDir * 2);
                int cJump = col + (cDir * 2);

                if (isValidPos(rJump, cJump)) {
                    if (grid[rJump][cJump] == EMPTY) {
                        int midPiece = grid[rNext][cNext];
                        if (midPiece != EMPTY && !isSameTeam(piece, midPiece)) {
                            jumps.add(new Movement(row, col, rJump, cJump));
                        }
                    }
                }
            }
        }
    }

    private boolean isSameTeam(int p1, int p2) {
        boolean p1Black = (p1 == BLACK || p1 == BLACK_KING);
        boolean p2Black = (p2 == BLACK || p2 == BLACK_KING);
        return p1Black == p2Black;
    }

    public boolean isValidPos(int r, int c) {
        return r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE;
    }

    public boolean isGameOver(int player) {
        return getLegalMoves(player).isEmpty();
    }

    public int countPieces(int pieceType) {
        int count = 0;
        for (int[] row : grid) {
            for (int p : row) {
                if (p == pieceType) count++;
            }
        }
        return count;
    }
}