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
    public int[] move(Cell[][] cell) {

            for (int[] move : getPreferredMoves()) {
                if (cell[move[0]][move[1]].getPlayer() == ' ') {
                    return move;
                }
            }
            assert false : "No empty cell?!";
            return null;

    }
}
