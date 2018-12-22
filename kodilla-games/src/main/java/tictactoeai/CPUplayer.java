package tictactoeai;

public class CPUplayer implements AIPlayer {

   @Override
    public int[][] getPreferredMoves() {
        int[][] preferredMoves = {
                {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
                {0, 1}, {1, 0}, {1, 2}, {2, 1}};
        return preferredMoves;
    }


    @Override
    public int[] move(Cell[][] cells) {

        for (int[] move : getPreferredMoves()) {
            if (cells[move[0]][move[1]].getCellContent() == CellContent.EMPTY) {
                return move;
            }
        }
        assert false : "No empty cell?!";
        return null;

    }
}
