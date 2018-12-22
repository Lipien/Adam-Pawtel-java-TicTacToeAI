package tictactoeai;

public interface AIPlayer {
    int[][] getPreferredMoves();
    int[] move(Cell[][] cells);

}