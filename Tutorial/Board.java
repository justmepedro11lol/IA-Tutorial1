
import java.util.Arrays;
import java.util.List;

interface Ilayout {

    /**
     * @return the children of the receiver.
     */
    List<Ilayout> children();

    /**
     * @return true if the receiver equals the argument l; return false
     * otherwise.
     */
    boolean isGoal(Ilayout l);

    /**
     * @return the cost for moving from the input config to the receiver.
     */
    double getG();
}

class Board implements Ilayout, Cloneable {

    private static final int dim = 3;
    private int board[][];

    public Board() {
        board = new int[dim][dim];
    }

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim) {
            throw new IllegalStateException("Invalid arg in Board constructor");
        }
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board[i][j] = Character.getNumericValue(str.charAt(si++));
            }
        }
    }

    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return Arrays.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }


    @Override
    public List<Ilayout> children() {
        return null;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return false;
    }

    @Override
    public double getG() {
        return 0;
    }
}
