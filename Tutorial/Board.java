import java.util.ArrayList;
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
    double getK();
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
        StringBuilder sb = new StringBuilder();
        // Percorre as linhas da matriz
        for (int i = 0; i < board.length; i++) {
            // Percorre as colunas de cada linha
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    sb.append(" "); // Substitui 0 por um espaço
                } else {
                    sb.append(board[i][j]); // Adiciona o elemento da matriz
                }
            }
            sb.append("\r\n"); // Adiciona uma nova linha após cada linha da matriz
        }

        return sb.toString();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board1 = (Board) o;
        return Arrays.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        int[] emptyPos = findEmptyPosition(); // encontrar a posição do 0 (espaço vazio)

        int row = emptyPos[0];
        int col = emptyPos[1];

        // Movimentos possíveis: cima, baixo, esquerda, direita
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (isValidMove(newRow, newCol)) {
                // Clonar o tabuleiro ANTES de fazer qualquer modificação
                Board newBoard = this.clone();
                newBoard.swap(row, col, newRow, newCol); // troca o 0 com a nova posição no clone
                children.add(newBoard);
            }
        }

        return children;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        if (!(l instanceof Board)) {
            return false; // Retorna false se l não for uma instância de Board
        }

        Board otherBoard = (Board) l; // Faz o cast para Board
        return Arrays.deepEquals(this.board, otherBoard.board); // Compara os dois tabuleiros
    }


    @Override
    public double getK() {
        return 0;
    }

    private int[] findEmptyPosition() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalStateException("Empty space not found in the board");
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < dim && col >= 0 && col < dim;
    }

    // Método para trocar dois elementos no tabuleiro
    private void swap(int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            clone.board = new int[dim][dim];  // Cria uma nova matriz
            for (int i = 0; i < dim; i++) {
                // Faz a cópia profunda da matriz
                clone.board[i] = Arrays.copyOf(this.board[i], dim);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}