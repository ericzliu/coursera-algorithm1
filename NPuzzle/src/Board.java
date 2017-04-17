import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {

    private final int[][] digits;
    private final int hamming;
    private final int manhattan;
    private final int dim;
    private final int emptyTileRow;
    private final int emptyTileCol;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(final int[][] blocks) {
        digits = deepCopy(blocks);
        dim = blocks.length;
        int t1 = 0;
        int t2 = 0;

        int voidRow = 0;
        int voidCol = 0;
        for (int i = 0; i < dim; i += 1) {
            for (int j = 0; j < dim; j += 1) {
                if (digits[i][j] != 0 && getTarget(i, j) != digits[i][j]) {
                    t1 += 1;
                    t2 += manhattan(i, j);
                } else if (digits[i][j] == 0) {
                    voidCol = j;
                    voidRow = i;
                }
            }
        }
        emptyTileCol = voidCol;
        emptyTileRow = voidRow;
        hamming = t1;
        manhattan = t2;
    }

    // board dimension n
    public int dimension() {
        return dim;
    }

    private int getTarget(final int i, final int j) {
        return i * dim + j + 1;
    }

    // number of blocks out of place
    public int hamming() {
        return hamming;
    }

    private int manhattan(final int i, final int j) {
        final int actual = digits[i][j];
        final int actRow = (actual - 1) / dim;
        final int actCol = (actual - 1) % dim;
        return Math.abs(actRow - i) + Math.abs(actCol - j);
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    private static int[][] deepCopy(int[][] tiles) {
        final int dim = tiles.length;
        int[][] c = new int[dim][dim];
        for (int i = 0; i < dim; i += 1) {
            System.arraycopy(tiles[i], 0, c[i], 0, dim);
        }
        return c;
    }

    private void exch(int[][] blocks, final int i1, final int j1, final int i2, final int j2) {
        int tmp = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = tmp;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] digitsCopy = deepCopy(digits);
        int i1 = -1;
        int j1 = -1;
        int i2 = -1;
        int j2 = -1;
        for (int i = 0; (i < dim) && (i1 == -1 || i2 == -1); i += 1) {
            for (int j = 0; (j < dim) && (i1 == -1 || i2 == -1); j += 1) {
                if (digitsCopy[i][j] != 0) {
                    if (i1 == -1) {
                        i1 = i;
                        j1 = j;
                    } else if (i2 == -1) {
                        i2 = i;
                        j2 = j;
                    }
                }
            }
        }
        exch(digitsCopy, i1, j1, i2, j2);
        return new Board(digitsCopy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board b = (Board) o;
        if (dim != b.dim) {
            return false;
        }
        for (int i = 0; i < dim; i += 1) {
            for (int j = 0; j < dim; j += 1) {
                if (digits[i][j] != b.digits[i][j])
                    return false;
            }
        }
        return true;
    }

    private static class BoardIterator implements Iterator<Board> {
        private final List<Board> boards;
        private int current;

        BoardIterator(List<Board> bds) {
            boards = bds;
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < boards.size();
        }

        @Override
        public Board next() {
            if (hasNext()) {
                Board board = boards.get(current);
                current += 1;
                return board;
            }
            throw new NoSuchElementException();
        }

    }

    private static class BoardIterable implements Iterable<Board> {
        private final List<Board> boards;

        BoardIterable(List<Board> bds) {
            boards = bds;
        }

        @Override
        public Iterator<Board> iterator() {
            return new BoardIterator(boards);
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int row = emptyTileRow;
        int col = emptyTileCol;
        List<Board> bds = new ArrayList<>();
        if (row < dim - 1) {
            exch(digits, row, col, row + 1, col);
            bds.add(new Board(digits));
            exch(digits, row, col, row + 1, col);
        }
        if (row > 0) {
            exch(digits, row, col, row - 1, col);
            bds.add(new Board(digits));
            exch(digits, row, col, row - 1, col);
        }
        if (col > 0) {
            exch(digits, row, col, row, col - 1);
            bds.add(new Board(digits));
            exch(digits, row, col, row, col - 1);
        }
        if (col < dim - 1) {
            exch(digits, row, col, row, col + 1);
            bds.add(new Board(digits));
            exch(digits, row, col, row, col + 1);
        }
        return new BoardIterable(bds);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        final int length = Math.max(2, String.valueOf(dim * dim - 1).length());
        StringBuilder builder = new StringBuilder();
        builder.append(dim);
        builder.append(System.lineSeparator());
        for (int i = 0; i < dim; i += 1) {
            for (int j = 0; j < dim; j += 1) {
                String s = String.valueOf(digits[i][j]);
                for (int k = 0; k < length - s.length(); k += 1) {
                    builder.append(' ');
                }
                builder.append(s);
                builder.append(' ');
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}