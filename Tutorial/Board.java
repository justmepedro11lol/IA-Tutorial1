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
        List<Ilayout> successors = new ArrayList<>();
        int emptyRow = 0;
        int emptyCol = 0;

        // Encontrar a posição da peça vazia (zero)
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
        }

        // Movimentos possíveis: cima, baixo, esquerda, direita
        int[] rowMoves = {-1, 1, 0, 0};
        int[] colMoves = {0, 0, -1, 1};

        for (int move = 0; move < 4; move++) {
            int newRow = emptyRow + rowMoves[move];
            int newCol = emptyCol + colMoves[move];

            if (newRow >= 0 && newRow < dim && newCol >= 0 && newCol < dim) {
                // Criar uma nova configuração de tabuleiro após o movimento
                Board newBoard = new Board(this.toString()); // Copia o estado atual
                // Troca a peça vazia com a peça adjacente
                newBoard.board[emptyRow][emptyCol] = newBoard.board[newRow][newCol];
                newBoard.board[newRow][newCol] = 0;
                successors.add(newBoard);
            }
        }

        return successors;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        
        return false;
    }

    @Override
    public double getG() {
        return 0;
    }

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}